#!/bin/sh
# ====================================================================
# Copyright (c) 2021, 2024 Obeo
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
GROUP="modeling.acceleo"

MAVEN_REPOSITORY_FOLDER=${WORKSPACE}/releng/maven/repository
UPDATE_FOLDER=${WORKSPACE}/releng/org.eclipse.acceleo.aql.update/target
UPDATE_ZIP="$(ls ${UPDATE_FOLDER}/org.eclipse.acceleo-*.zip | sort -V | tail -n1)"

ZIP_NAME=$(echo ${UPDATE_ZIP} | sed 's/.*\(org.eclipse.acceleo-.*.zip\)$/\1/')
QUALIFIER=$(echo ${UPDATE_ZIP} | sed 's/.*org.eclipse.acceleo-\(.*\).zip$/\1/')

# add maven repository to the p2 zip file
echo Adding maven repository to p2 repository zip file
pushd ${MAVEN_REPOSITORY_FOLDER}
  zip -ur ${UPDATE_ZIP} .
popd

P2_TIMESTAMP=$(date +"%s000")

# parameter for nightly clean up, keeps NIGHTLY_COUNT for the current version.
NIGHTLY_COUNT=10
VERSION=$(echo ${QUALIFIER} | cut -d"." -f1-3)

ssh "${SSH_ACCOUNT}" mkdir -p ${NIGHTLIES_FOLDER}/${QUALIFIER}
scp -rp ${UPDATE_ZIP} "${SSH_ACCOUNT}:${NIGHTLIES_FOLDER}/${QUALIFIER}"

# Unzip the repository and change the permissions
ssh "${SSH_ACCOUNT}" -T <<EOF
  pushd ${NIGHTLIES_FOLDER}/${QUALIFIER}
    unzip "${ZIP_NAME}"
    rm ${ZIP_NAME}
  popd

  # make sure permissions are for the acceleo group
  chgrp -R ${GROUP} ${NIGHTLIES_FOLDER}/${QUALIFIER}
  chmod -R g+w ${NIGHTLIES_FOLDER}/${QUALIFIER}

  pushd ${NIGHTLIES_FOLDER}/latest
    rm -r *
    cp -r ../${QUALIFIER}/* .
  popd

  # remove older nightly
  pushd ${NIGHTLIES_FOLDER}
    ls -tr | grep -F ${VERSION} | head -n $(expr $(ls -tr | grep -F ${VERSION} | wc -l) - ${NIGHTLY_COUNT}) | xargs rm -rf
  popd
EOF
