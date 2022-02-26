#!/bin/sh -e
# relative path to the pass file must be added as argument
PWLOC=$1/.alm_host
DST_HOST=192.168.3.188
DST_HOST_USER=opentrends
DST_TARGET=${DST_HOST_USER}@${DST_HOST}

echo "Deploying to $DST_TARGET"
echo "Rebuilding image..."
timeout 120 sshpass -f$PWLOC ssh -o StrictHostKeyChecking=no ${DST_TARGET} "docker ps -a && cd vue-reader-simulator/ && docker-compose pull vue-reader-simulator && docker-compose up -d --build vue-reader-simulator && docker image prune -f"
