# generated, do not modify
FROM centos:7

RUN yum -y update &&\
    yum -y install --setopt=tsflags=nodocs java-1.8.0-openjdk &&\
    yum clean all

# install jdk
ENV JAVA_HOME /usr/lib/jvm/jre-1.8.0-openjdk
ENV PATH $JAVA_HOME/bin:$PATH

# copy app files
ADD . /app
RUN chmod +x /app/bin/gtfs-lookup-service
RUN rm -f /app/Dockerfile

# cleanup
RUN rm -rf /tmp
RUN mkdir /tmp
RUN chmod a+rw /tmp

EXPOSE 8080

# default command to execute when creating a new container
WORKDIR /app
ENTRYPOINT ["/app/bin/gtfs-lookup-service"]
