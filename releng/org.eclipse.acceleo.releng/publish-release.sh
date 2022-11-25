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

PROJECT_NAME="Acceleo"
MILESTONES_FOLDER="/home/data/httpd/download.eclipse.org/acceleo/updates/milestones"
RELEASES_FOLDER="/home/data/httpd/download.eclipse.org/acceleo/updates/releases"
DROPS_FOLDER="/home/data/httpd/download.eclipse.org/acceleo/downloads/drops"
ZIP_PREFIX="acceleo-update-"
GROUP="modeling.acceleo"

if [[ ! ${QUALIFIER} =~ ^[0-9]\.[0-9]\.[0-9]+\.[0-9]{12}$ ]]
then
  echo "$QUALIFIER doesn't match the expected format x.x.x.yyyyMMddhhmm"
  exit 1
fi

IFS=. read MAJOR MINOR MICRO TIMESTAMP <<<"${QUALIFIER}"

VERSION_SHORT=${MAJOR}.${MINOR}
VERSION=${MAJOR}.${MINOR}.${MICRO}

if ssh ${SSH_ACCOUNT} "[ ! -d ${MILESTONES_FOLDER}/${VERSION_SHORT}/S${TIMESTAMP} ]"
then
  echo "couldn't find build with qualifier $QUALIFIER in the promoted milestones"
  exit 1
fi

echo "promoting milestone S${TIMESTAMP} as release ${VERSION}"

ssh ${SSH_ACCOUNT} << EOSSH
  ## copy the drops
  
  cp -r ${DROPS_FOLDER}/${VERSION}/S${TIMESTAMP} ${DROPS_FOLDER}/${VERSION}/R${TIMESTAMP}
  mv ${DROPS_FOLDER}/${VERSION}/R${TIMESTAMP}/${ZIP_PREFIX}*.zip ${DROPS_FOLDER}/${VERSION}/R${TIMESTAMP}/${ZIP_PREFIX}${VERSION}.zip
  rm ${DROPS_FOLDER}/${VERSION}/R${TIMESTAMP}/${ZIP_PREFIX}*.zip.md5
  md5sum ${DROPS_FOLDER}/${VERSION}/R${TIMESTAMP}/${ZIP_PREFIX}${VERSION}.zip > ${DROPS_FOLDER}/${VERSION}/R${TIMESTAMP}/${ZIP_PREFIX}${VERSION}.zip.md5
  chgrp -R ${GROUP} ${DROPS_FOLDER}/${VERSION}/R${TIMESTAMP}

  ## copy the update site

  UPDATE_ROOT_COMPOSITE=false
  if [ ! -d ${RELEASES_FOLDER}/${VERSION_SHORT} ]
  then
    mkdir -p ${RELEASES_FOLDER}/${VERSION_SHORT}
    chgrp -R ${GROUP} ${RELEASES_FOLDER}/${VERSION_SHORT}
    UPDATE_ROOT_COMPOSITE=true
  fi
  cp -r ${MILESTONES_FOLDER}/${VERSION_SHORT}/S${TIMESTAMP} ${RELEASES_FOLDER}/${VERSION_SHORT}/R${TIMESTAMP}
  chgrp -R ${GROUP} ${RELEASES_FOLDER}/${VERSION_SHORT}/R${TIMESTAMP}
  
  ## update the "latest" update site

  rm -r ${RELEASES_FOLDER}/latest/*
  cp -r ${RELEASES_FOLDER}/${VERSION_SHORT}/R${TIMESTAMP}/* ${RELEASES_FOLDER}/latest/

  ## update the releases composite
  ## The ant script we're using requires Java 8
  export JAVA_HOME=/shared/common/jdk1.8.0_x64-latest
  cd ${RELEASES_FOLDER}/${VERSION_SHORT}
  /shared/common/apache-ant-latest/bin/ant -f /shared/modeling/tools/promotion/manage-composite.xml add -Dchild.repository=R${TIMESTAMP} -Dcomposite.name="${PROJECT_NAME} ${VERSION_SHORT} releases"

  if [ "$UPDATE_ROOT_COMPOSITE" = true ]
  then
    cd ${RELEASES_FOLDER}
    /shared/common/apache-ant-latest/bin/ant -f /shared/modeling/tools/promotion/manage-composite.xml add -Dchild.repository=${VERSION_SHORT} -Dcomposite.name="${PROJECT_NAME} releases"
  fi
EOSSH