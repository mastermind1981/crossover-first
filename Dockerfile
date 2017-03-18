FROM ubuntu:14.04

# INSTALL JAVA 8
RUN apt-get update
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN apt-get install oracle-java8-installer -y --force-yes
RUN apt-get install oracle-java8-set-default

# INSTALL SCITOOLS UNDERSTAND
RUN wget http://builds.scitools.com/all_builds/b844/Understand/Understand-4.0.844-Linux-64bit.tgz && tar -zxvf Understand-4.0.844-Linux-64bit.tgz
RUN echo "Server: scitools-license.devfactory.com 00000000 9000" > /scitools/conf/license/locallicense.dat

# INSTALL LIBS
RUN apt-get install libxrender1 -y
RUN apt-get install libxi6 libgconf-2-4 -y
RUN apt-get install libxtst6 -y
RUN apt-get install git -y
RUN apt-get install curl -y


# CONFIGURE ENV
ENV LD_LIBRARY_PATH /usr/lib/jvm/java-8-oracle/jre/lib/amd64
ENV PATH .:$PATH:/scitools/bin/linux64
ENV CLASSPATH .:$CLASSPATH:/scitools/bin/linux64/Java/com.scitools.understand.plugin_1.1.3.jar

RUN mkdir app && mkdir -p src/main/resources/analyze/udb && mkdir -p src/main/resources/analyze/repository
#COPY scripts/*.sh /scripts/
COPY clean_analyzed_files.sh clean_analyzed_files.sh
COPY create_udb.sh create_udb.sh
COPY delete_given_analyzed_files.sh delete_given_analyzed_files.sh
COPY build/libs/deadcode-detector-0.1.0.jar /app/app.jar

RUN chmod +x *.sh

EXPOSE 29011 80
ENTRYPOINT [ "sh", "-c", "java -jar /app/app.jar --spring.profiles.active=production" ]
