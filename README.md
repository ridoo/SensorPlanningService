# Sensor Planning Service (SPS)

**Standardized tasking interface for devices and support systems.**

_The 52°North SPS is an implementation of the OGC Sensor Planning Service Standard, version 2.0.1.
It provides a development framework for arbitrary sensor plugins along with the standardized tasking interface of the
OGC SPS Specification (OGC 09-000)._

The OGC Sensor Planning Service (SPS) is a Web service to acquire information about the capabilities of sensors and how to task them through specified operations. Remarkably, the SPS does not allow retrieval of observations, as this is done by a SOS. Instead it may be used to dynamically change the parametrization of sensors to adjust them to a new observation scenario.

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

The following main frameworks are used to provide this Web application:

- [Spring MVC](https://spring.io/) 
- [Hibernate](http://hibernate.org/) 

## References
* The [SPS demo application](http://sensorweb.demo.52north.org/http://sensorweb.demo.52north.org/52n-sps-2.0/)
has a sensor plugin registered which simulates an execution. The sensor demo plugin is shipped with the SPS
and can be used for both, playing around with the interface and to gather how custom plugin implementations 
integrate into the SPS framework.
* Further information can be accessed from the [52n SPS Wiki](https://wiki.52north.org/bin/view/SensorWeb/SensorPlanningService).

## License
The client is published under the [GNU General Public License v2 (GPLv2)](http://www.gnu.org/licenses/gpl-2.0.html).

## Changelog
TBD, for now refer to https://github.com/52North/series-rest-api/pulls?q=is%3Apr+is%3Aclosed

## Contributing
You can get support in the community mailing list and forums:

    http://52north.org/resources/mailing-list-and-forums/

If you encounter any issues with the software or if you would like to see
certain functionality added, feel free to [create an new issue](https://github.com/52North/SensorPlanningService/issues/new).

We try to follow [the GitFlow model](http://nvie.com/posts/a-successful-git-branching-model/), 
although we do not see it that strict. 

However, make sure to do pull requests for features, hotfixes, etc. by
making use of GitFlow. Altlassian provides [a good overview]
(https://www.atlassian.com/de/git/workflows#!workflow-gitflow). of the 
most common workflows.

## Contact
Henning Bredel (h.bredel@52north.org)


## Quick Start

The 52°North SPS comes as Java Web Archive which can be deployed in a servlet container like Jetty
or Apache Tomcat. Once deployed you must stop the container (or the web application respectively) 
to start configuration. The main configuration files are

   * `WEB-INF/classes/hibernate.cfg.xml`
   * `WEB-INF/classes/logback.xml`
   * `WEB-INF/classes/spring-database-config.xml`
 
The [52n SPS 2.0 Wiki](https://wiki.52north.org/bin/view/SensorWeb/52nSPSv200-Documentation#Installation) describes
gives you more detailled information how to install and configure an SPS instance. It also instructions how 
to register a sensor plugin (which actually is a custom Web interface and not specified by the SPS standard).
 
