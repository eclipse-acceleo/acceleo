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

if [ ${REFERENCE_TARGET_PLATFORM} != ${PLATFORM} ]; then
  exit 0
fi

# Exit on error
set -e

# The SSH account to use
export SSH_ACCOUNT="genie.acceleo@projects-storage.eclipse.org"

NIGHTLIES_FOLDER="/home/data/httpd/download.eclipse.org/accleeo/updates/nightly"
GROUP="acceleo"

UPDATE_FOLDER=${WORKSPACE}/releng/org.eclipse.acceleo.aql.update/target

UPDATE_ZIP="$(ls ${UPDATE_FOLDER}/org.eclipse.acceleo-*.zip | sort -V | tail -n1)"

ZIP_NAME=$(echo ${UPDATE_ZIP} | sed 's/.*\(org.eclipse.acceleo-.*.zip\)$/\1/')
QUALIFIER=$(echo ${UPDATE_ZIP} | sed 's/.*org.eclipse.acceleo-\(.*\).zip$/\1/')

P2_TIMESTAMP=$(date +"%s000")

# Transfer the zipped update site
ssh "${SSH_ACCOUNT}" mkdir -p ${NIGHTLIES_FOLDER}/${QUALIFIER}
scp -rp ${UPDATE_ZIP} "${SSH_ACCOUNT}:${NIGHTLIES_FOLDER}/${QUALIFIER}"

# Unzip the transfered zipped update site
ssh "${SSH_ACCOUNT}" -T <<EOF
  pushd ${NIGHTLIES_FOLDER}/${QUALIFIER}
    unzip "${ZIP_NAME}" -d ./
    rm ${ZIP_NAME}
  popd
  
  # make sure permissions are update for the acceleo group
  chgrp -R ${GROUP} ${NIGHTLIES_FOLDER}/${QUALIFIER}
  chmod -R g+w ${NIGHTLIES_FOLDER}/${QUALIFIER}

  pushd ${NIGHTLIES_FOLDER}/latest
    rm -r *
    cp -r ../${QUALIFIER}/* .
  popd
EOF
