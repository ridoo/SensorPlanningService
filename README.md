# Sensor Planning Service (SPS)

The 52°North SPS is an implementation of the OGC Sensor Planning Service Standard, version 2.0.1.
It provides a framework for sensor plugins along with the standardized tasking interface of the
OGC SPS Specification (OGC 09-000).

The Sensor Planning Service (SPS) is intended to provide a standard interface to devices and support systems. 

A full OGC SPS implementation provides access to 
- the different stages of planning,
- scheduling, 
- tasking, 
- collection, 
- processing, 
- archiving, and 
- distribution of resulting observation data

The current 52n implementation status of the SPS standard covers
- Conformance class `Core` (GetCapabilities,DescribeTasking, Submit, GetStatus, GetTask and DescribeResultAccess Operations) 
- SPI interface to implement custom sensor plugins
- Demo plugin which simulates sensor tasking and execution
- Custom admin Web interface to register devices

Further information can be accessed from the [52n SPS Wiki](https://wiki.52north.org/bin/view/SensorWeb/SensorPlanningService).

The following main frameworks are used to provide this Web application:

- [Spring MVC](https://angularjs.org/) 
- [Hibernate](http://hibernate.org/) 
   
## License

The client is published under the [GNU General Public License v2 (GPLv2)](http://www.gnu.org/licenses/gpl-2.0.html).

## Demo

* The [SPS demo application](http://sensorweb.demo.52north.org/http://sensorweb.demo.52north.org/52n-sps-2.0/)
has a sensor plugin registered which simulates an execution. The sensor demo plugin is shipped with the SPS
and can be used for both, playing around with the interface and to gather how custom plugin implementations 
integrate into the SPS framework.

## Getting started and configuration

The 52°North SPS comes as Java Web Archive which can be deployed in a servlet container like Jetty
or Apache Tomcat. Once deployed you must stop the container (or the web application respectively) 
to start configuration. The main configuration files are

   * `WEB-INF/classes/hibernate.cfg.xml`
   * `WEB-INF/classes/logback.xml`
   * `WEB-INF/classes/spring-database-config.xml`
 
The [52n SPS 2.0 Wiki](https://wiki.52north.org/bin/view/SensorWeb/52nSPSv200-Documentation#Installation) describes
gives you more detailled information how to install and configure an SPS instance. It also instructions how 
to register a sensor plugin (which actually is a custom Web interface and not specified by the SPS standard).
 
## Download, Support and Contact

The latest release of 52°North SPS can be downloaded from this website:

    http://52north.org/downloads/sensor-web/sps

You can get support in the community mailing list and forums:

    http://52north.org/resources/mailing-list-and-forums/

If you encounter any issues with the software or if you would like to see
certain functionality added, feel free to [create an new issue](https://github.com/52North/SensorPlanningService/issues/new).
