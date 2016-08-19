# omspipelineweb
Brian and Dylan's Intern Project for OMS

### Launching your Open Stack Instance
1. Launch Instance
  * Instance Name: 
  * Instance Flavor: **m1.small** (yum)
  * Instance Count: **1**
  * Instance Boot Source: **Image**
    * Ubuntu 16.04.1 LTS Xenial Xerus (cloudimg)
  * Security: Go to the Access and Security tab then add an SSH keypair

### SSH
1. SSHing into the new instance
  * Go to terminal, then type 
  
    ```
    cd ~/.ssh && ssh -i [your private key] ubuntu@[IP Address] 
    ```
    
  * For Example
    ```
    cd ~/.ssh && ssh -i myKey.key ubuntu@10.9.99.11
    ```

### Set Up Ubuntu Environment

1. Clone the repository into your home folder
 * Command:
  ```
  cd ~/ && git clone https://github.gapinc.com/Dybrown/omspipelineweb.git
  ```

2. Install Oracle JDK on the Ubuntu Environment
 * Commands:
 ```
 sudo apt-add-repository ppa:webupd8team/java
 sudo apt-get update
 sudo apt-get install oracle-java8-installer
 ```
 * java -version should now say 1.8

3. Update the IP in the writeSQLTablesAndChangeHTML.js to your instance’s IP
 * Commands:
 ```
 cd ~/omspipelineweb/src/main/webapp/
 sudo vi writeSQLTablesAndChangeHTML.js
 ```
 * Edit the $.ajax call’s URL
 * “url” : “http://[your IP Here]/MainServ”,
 * For example -> “url” : “http://omspipelinestatus.gapinc.dev/MainServ”

4. Change the timezone in Ubuntu
 * Commands:
 ```
 sudo dpkg-reconfigure tzdata
 ```
 * Select US
 * Select OK
 * Select Eastern
 * Press Enter/Return
 
5. Crontab Scheduler for Deploying the App
 * Commands:
 ```
 sudo crontab -e
 ```
 * Add this to the file:
 ```
 @reboot /home/ubuntu/omspipelineweb/runOMSPipeline.sh
 0 0 * * * reboot
 ```
 
6. Add your host to sudo
 * Commands:
 ```
 cd /etc/
 sudo vi hosts
 ```
 * Add this to the beginning of the file:
 ```
 127.0.1.1 [instancename]
 ```
 
7. Attach the Instance's static IP to our DNS name
 * Optional
 * Contact the Open Stack team @ HQ-OPENSTACK-PLATFORM-TEAM-DG@gap.com
 * In the message, provide your IP address and the fully qualified domain name
 * For example, http://omspipelineweb.gapinc.dev (preferred)
 * Don't forget to return to step 3 after receiving the DNS name.

### Running the OMS Pipeline Web Application
1. From the shell script:
 * Commands:
 ```
 sudo ~/omspipelineweb/runOMSPipeline.sh
 ```
 
2. From the separate commands:
 * Commands
 ```
 cd ~/omspipelineweb/
 sudo ./gradlew clean
 sudo ./gradlew build
 java -jar build/libs/gs-spring-boot-0.1.0.jar
 ```

3. Visit the website at your IP Address or DNS Name
 * For our deployment, we used the site:
 ```
 http://omspipelinestatus.gapinc.dev/
 ```

    
