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
package org.n52.sps.sensor.result;

import net.opengis.ows.x11.LanguageStringType;
import net.opengis.sps.x20.DataNotAvailableType;
import net.opengis.sps.x20.DescribeResultAccessResponseType.Availability;
import net.opengis.sps.x20.DescribeResultAccessResponseType.Availability.Unavailable;

import org.n52.sps.service.core.DataUnavailableCode;

public final class DataNotAvailable implements ResultAccessAvailabilityDescriptor {

    private final Availability availability = Availability.Factory.newInstance();
    
    public DataNotAvailable(LanguageStringType... messages) {
        Unavailable unavailable = availability.addNewUnavailable();
        DataNotAvailableType dataNotAvailable = unavailable.addNewDataNotAvailable();
        dataNotAvailable.setUnavailableCode(DataUnavailableCode.DATA_NOT_AVAILABLE.getCode());
        dataNotAvailable.setMessageArray(messages);
    }

    public Availability getResultAccessibility() {
        return availability;
    }

    public boolean isDataAvailable() {
        return false;
        
    }
    
}
