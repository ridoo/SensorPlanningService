/**
 * ﻿Copyright (C) 2012-${latestYearOfContribution} 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as publishedby the Free
 * Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of the
 * following licenses, the combination of the program with the linked library is
 * not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed under
 * the aforementioned licenses, is permitted by the copyright holders if the
 * distribution is compliant with both the GNU General Public License version 2
 * and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package org.n52.sps.control.xml;

import net.opengis.sps.x20.CancelDocument;
import net.opengis.sps.x20.CapabilitiesDocument;
import net.opengis.sps.x20.ConfirmDocument;
import net.opengis.sps.x20.DescribeResultAccessDocument;
import net.opengis.sps.x20.DescribeTaskingDocument;
import net.opengis.sps.x20.GetCapabilitiesDocument;
import net.opengis.sps.x20.GetFeasibilityDocument;
import net.opengis.sps.x20.GetStatusDocument;
import net.opengis.sps.x20.GetTaskDocument;
import net.opengis.sps.x20.ReserveDocument;
import net.opengis.sps.x20.SubmitDocument;
import net.opengis.sps.x20.UpdateDocument;
import net.opengis.swes.x20.DescribeSensorDocument;
import net.opengis.swes.x20.UpdateSensorDescriptionDocument;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.n52.ows.exception.NoApplicableCodeException;
import org.n52.ows.exception.OperationNotSupportedException;
import org.n52.ows.exception.OwsException;
import org.n52.ows.exception.OwsExceptionReport;
import org.n52.sps.control.RequestDelegationHandler;
import org.n52.sps.service.SensorPlanningService;
import org.n52.sps.service.core.BasicSensorPlanner;
import org.n52.sps.service.core.SensorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Delegator to dispatch incoming XML requests to the appropriate operation handler of the
 * {@link SensorPlanningService}. No validation takes place, so if you want to only delegate validated
 * requests decorate it with an {@link XmlValidationDelegate}.
 * 
 * @see XmlValidationDelegate
 */
public class XmlDelegate implements RequestDelegationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlDelegate.class);

    private SensorPlanningService service;

    private XmlObject request;

    public XmlDelegate(SensorPlanningService service, XmlObject request) throws OwsException {
        this.service = service;
        this.request = request;
    }

    XmlObject getRequest() {
        return request;
    }
    
    SensorPlanningService getService() {
        return service;
    }

    public XmlObject delegate() throws OwsException, OwsExceptionReport {
        SchemaType schemaType = request.schemaType();

        /*
         * ######################## MANDATORY OPERATIONS ########################
         */

        SensorProvider sensorProvider = service.getSensorProvider();
        BasicSensorPlanner basicSensorPlanner = service.getBasicSensorPlanner();
        if (schemaType == DescribeSensorDocument.type) {
            DescribeSensorDocument describeSensor = (DescribeSensorDocument) request;
            return sensorProvider.describeSensor(describeSensor);
        }
        else if (schemaType == DescribeResultAccessDocument.type) {
            DescribeResultAccessDocument describeResultAccess = (DescribeResultAccessDocument) request;
            return basicSensorPlanner.describeResultAccess(describeResultAccess);
        }
        else if (schemaType == DescribeTaskingDocument.type) {
            DescribeTaskingDocument describeTasking = (DescribeTaskingDocument) request;
            return basicSensorPlanner.describeTasking(describeTasking);
        }
        else if (schemaType == GetCapabilitiesDocument.type) {
            GetCapabilitiesDocument getCapabilities = (GetCapabilitiesDocument) request;
            XmlObject capabilities = basicSensorPlanner.getCapabilities(getCapabilities);
            service.interceptCapabilities((CapabilitiesDocument) capabilities);
            return capabilities;
        }
        else if (schemaType == GetStatusDocument.type) {
            GetStatusDocument getStatus = (GetStatusDocument) request;
            return basicSensorPlanner.getStatus(getStatus);
        }
        else if (schemaType == GetTaskDocument.type) {
            GetTaskDocument getTask = (GetTaskDocument) request;
            return basicSensorPlanner.getTask(getTask);
        }
        else if (schemaType == SubmitDocument.type) {
            SubmitDocument submit = (SubmitDocument) request;
            return basicSensorPlanner.submit(submit);
        }

        /*
         * ######################## OPTIONAL OPERATIONS ########################
         */

        // throw OperationNotSupported according to REQ 3:
        // http://www.opengis.net/spec/SPS/2.0/req/exceptions/codes

        else if (schemaType == UpdateDocument.type) {
            if ( !service.isSupportingTaskUpdaterProfile()) {
                throw new OperationNotSupportedException("Update");
            }
            UpdateDocument update = (UpdateDocument) request;
            return service.getTaskUpdater().update(update);
        }
        else if (schemaType == CancelDocument.type) {
            if ( !service.isSupportingTaskCancellerProfile()) {
                throw new OperationNotSupportedException("Cancel");
            }
            CancelDocument cancel = (CancelDocument) request;
            return service.getTaskCanceller().cancel(cancel);
        }
        else if (schemaType == UpdateSensorDescriptionDocument.type) {
            if ( !service.isSupportingSensorHistoryProviderProfile()) {
                throw new OperationNotSupportedException("UpdateSensorDescription");
            }
            UpdateSensorDescriptionDocument updateSensorDescription = (UpdateSensorDescriptionDocument) request;
            return service.getSensorDescriptionManager().updateSensorDescription(updateSensorDescription);
        }
        else if (schemaType == ConfirmDocument.type) {
            if ( !service.isSupportingReservationManagerProfile()) {
                throw new OperationNotSupportedException("Confirm");
            }
            ConfirmDocument confirm = (ConfirmDocument) request;
            return service.getReservationManager().confirm(confirm);
        }
        else if (schemaType == ReserveDocument.type) {
            if ( !service.isSupportingReservationManagerProfile()) {
                throw new OperationNotSupportedException("Reserve");
            }
            ReserveDocument reserve = (ReserveDocument) request;
            return service.getReservationManager().reserve(reserve);
        }
        else if (schemaType == GetFeasibilityDocument.type) {
            if ( !service.isSupportingFeasibilityControllerProfile()) {
                throw new OperationNotSupportedException("GetFeasibility");
            }
            GetFeasibilityDocument getFeasibility = (GetFeasibilityDocument) request;
            return service.getFeasibilityController().getFeasibility(getFeasibility);
        }
        else {
            LOGGER.error("No delegation handler for '{}' request.", schemaType);
            throw new NoApplicableCodeException(OwsException.BAD_REQUEST);
        }
    }

}
