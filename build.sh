#!/bin/bash
apt-get update
apt-get upgrade -y
apt-get install xvfb libxrender1 libxtst6 libxi6 -y
apt-get install openjdk-8-jre -y
apt-get install openjdk-8-jdk -y
apt-get install zip unzip -y
curl -s "https://get.sdkman.io" | bash
chmod +x "$HOME/.sdkman/bin/sdkman-init.sh"
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install gradle
gradle
sleep 10
xvfb-run gradle build
