#!/bin/sh
# ====================================================================
# Copyright (c) 2021 Obeo
# This program and the accompanying materials
# are made available under the terms of the Eclipse Public License 2.0
# which accompanies this distribution, and is available at
# https://www.eclipse.org/legal/epl-2.0
#
# Contributors:
#    Obeo - initial API and implementation
# ====================================================================

# Exit on error
set -e

# The SSH account to use
export SSH_ACCOUNT="genie.acceleo@projects-storage.eclipse.org"

NIGHTLIES_FOLDER="/home/data/httpd/download.eclipse.org/acceleo/updates/nightly"
MILESTONES_FOLDER="/home/data/httpd/download.eclipse.org/acceleo/updates/milestones"
DROPS_FOLDER="/home/data/httpd/download.eclipse.org/acceleo/downloads/drops"
ZIP_PREFIX="acceleo-update-"
GROUP="modeling.acceleo"

if [[ ! ${QUALIFIER} =~ ^[0-9]\.[0-9]\.[0-9]+\.[0-9]{12}$ ]]
then
  echo "$QUALIFIER doesn't match the expect format x.x.x.yyyyMMddhhmm"
  exit 1
fi

if ssh ${SSH_ACCOUNT} "[ ! -d ${NIGHTLIES_FOLDER}/${QUALIFIER} ]"
then
  echo "couldn't find build with qualifier $QUALIFIER in the promoted nightlies"
  exit 1
fi

echo "promoting build $QUALIFIER as milestone $ALIAS"

IFS=. read MAJOR MINOR MICRO TIMESTAMP <<<"${QUALIFIER}"

VERSION_SHORT=${MAJOR}.${MINOR}
VERSION=${MAJOR}.${MINOR}.${MICRO}

UPDATE_ROOT_COMPOSITE=false
if ssh ${SSH_ACCOUNT} "[ ! -d ${MILESTONES_FOLDER}/${VERSION_SHORT} ]"
then
  UPDATE_ROOT_COMPOSITE=true
fi

ssh ${SSH_ACCOUNT} << EOSSH
  if ${UPDATE_ROOT_COMPOSITE}
  then
    mkdir -p ${MILESTONES_FOLDER}/${VERSION_SHORT}
    chgrp ${GROUP} ${MILESTONES_FOLDER}/${VERSION_SHORT}
  fi
  
  ## copy the nightly to its "milestones" location
  cp -r ${NIGHTLIES_FOLDER}/${QUALIFIER} ${MILESTONES_FOLDER}/${VERSION_SHORT}/S${TIMESTAMP}
  chgrp -R ${GROUP} ${MILESTONES_FOLDER}/${VERSION_SHORT}/S${TIMESTAMP}

  ## Create a compressed archive of this repository and place it in the drops folder
  if [ ! -d ${DROPS_FOLDER}/${VERSION} ]
  then
    mkdir -p ${DROPS_FOLDER}/${VERSION}
    chgrp ${GROUP} ${DROPS_FOLDER}/${VERSION}
  fi

  mkdir -p ${DROPS_FOLDER}/${VERSION}/S${TIMESTAMP}
  pushd ${MILESTONES_FOLDER}/${VERSION_SHORT}/S${TIMESTAMP}
    zip -2 -r ${DROPS_FOLDER}/${VERSION}/S${TIMESTAMP}/${ZIP_PREFIX}${ALIAS}.zip *
  popd
  md5sum ${DROPS_FOLDER}/${VERSION}/S${TIMESTAMP}/${ZIP_PREFIX}${ALIAS}.zip > ${DROPS_FOLDER}/${VERSION}/S${TIMESTAMP}/${ZIP_PREFIX}${ALIAS}.zip.md5
  chgrp -R ${GROUP} ${DROPS_FOLDER}/${VERSION}/S${TIMESTAMP}
  
  ## Update the composite update site with this new child
  ## The ant script we're using requires Java 8
  export JAVA_HOME=/shared/common/jdk1.8.0_x64-latest
  cd ${MILESTONES_FOLDER}/${VERSION_SHORT}
  /shared/common/apache-ant-latest/bin/ant -f /shared/modeling/tools/promotion/manage-composite.xml add -Dchild.repository=S${TIMESTAMP}
  
  if [ "$UPDATE_ROOT_COMPOSITE" = true ]
  then
    cd ${MILESTONES_FOLDER}
    /shared/common/apache-ant-latest/bin/ant -f /shared/modeling/tools/promotion/manage-composite.xml add -Dchild.repository=${VERSION_SHORT}
  fi
EOSSH