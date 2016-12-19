/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.ballerina.core.model.values;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.util.AXIOMUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ballerina.core.exception.BallerinaException;
import org.wso2.ballerina.core.message.BallerinaMessageDataSource;

import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;


/**
 * {@code XMLValue} represents a XML value in Ballerina.
 *
 * @since 1.0.0
 */
public class XMLValue extends BallerinaMessageDataSource implements BValue<OMElement> {

    private static final Logger LOG = LoggerFactory.getLogger(XMLValue.class);

    private OMElement omElement;

    private OutputStream outputStream;

    /**
     * Initialize a {@link XMLValue} from a XML string.
     *
     * @param xmlValue A XML string
     */
    public XMLValue(String xmlValue) {
        if (xmlValue != null) {
            try {
                omElement = AXIOMUtil.stringToOM(xmlValue);
            } catch (XMLStreamException e) {
                LOG.error("Cannot create OMElement from given String, maybe malformed String ", e);
            }
        }
    }

    /**
     * Initialize a {@link XMLValue} from a {@link org.apache.axiom.om.OMElement} object.
     *
     * @param omElement xml object
     */
    public XMLValue(OMElement omElement) {
        this.omElement = omElement;
    }

    /**
     * Create a {@link XMLValue} from a {@link InputStream}.
     *
     * @param inputStream Input Stream
     */
    public XMLValue(InputStream inputStream) {
        if (inputStream != null) {
            try {
                omElement = new StAXOMBuilder(inputStream).getDocumentElement();
            } catch (XMLStreamException e) {
                LOG.error("Cannot create OMElement from given source, maybe malformed XML Stream", e);
            }
        }
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public OMElement getValue() {
        return omElement;
    }

    @Override
    public void serializeData() {
        try {
            this.omElement.serialize(this.outputStream);
        } catch (XMLStreamException e) {
            throw new BallerinaException("Error occurred during writing the message to the output stream", e);
        }
    }

    @Override
    public StringValue getString() {
        return new StringValue(this.getValue().toString());
    }
}