/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.ballerina.core.model;

import org.wso2.ballerina.core.model.expressions.Expression;

import java.util.List;

/**
 * A {@code Connection} represents the instantiation of a connector with a particular configuration.
 *
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ConnectorDcl implements Node {

    /* Name of the Connector which Connection is instantiated against */
    SymbolName connectorName;

    /* Name of the Connection instance */
    SymbolName varName;

    Expression[] argExprs;

    public ConnectorDcl(SymbolName connectorName, SymbolName varName, Expression[] argExprs) {
        this.connectorName = connectorName;
        this.varName = varName;
        this.argExprs = argExprs;
    }

    /**
     * Get the name of the {@code Connector} which Connection is instantiated against
     *
     * @return name of the Connector
     */
    public SymbolName getConnectorName() {
        return connectorName;
    }

    /**
     * Get the {@code Identifier} of the Connection instance
     *
     * @return identifier of the Connection instance
     */
    public SymbolName getVarName() {
        return varName;
    }

    /**
     * Get values of the arguments
     *
     * @return list of argument values
     */
    public Expression[] getArgExprs() {
        return argExprs;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    /**
     *
     */
    public static class ConnectorDclBuilder {
        private SymbolName connectorName;
        private SymbolName varName;
        private List<Expression> exprList;

        public void setConnectorName(SymbolName connectorName) {
            this.connectorName = connectorName;
        }

        public void setVarName(SymbolName varName) {
            this.varName = varName;
        }

        public void setExprList(List<Expression> exprList) {
            this.exprList = exprList;
        }

        public ConnectorDcl build() {
            return new ConnectorDcl(
                    connectorName,
                    varName,
                    exprList.toArray(new Expression[exprList.size()]));
        }
    }
}