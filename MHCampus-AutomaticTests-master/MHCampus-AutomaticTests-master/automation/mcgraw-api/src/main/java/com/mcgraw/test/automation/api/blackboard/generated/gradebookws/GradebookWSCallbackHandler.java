
/**
 * GradebookWSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package com.mcgraw.test.automation.api.blackboard.generated.gradebookws;

    /**
     *  GradebookWSCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class GradebookWSCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public GradebookWSCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public GradebookWSCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getGradebookTypes method
            * override this method for handling normal response from getGradebookTypes operation
            */
           public void receiveResultgetGradebookTypes(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetGradebookTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGradebookTypes operation
           */
            public void receiveErrorgetGradebookTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveGradingSchemas method
            * override this method for handling normal response from saveGradingSchemas operation
            */
           public void receiveResultsaveGradingSchemas(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.SaveGradingSchemasResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveGradingSchemas operation
           */
            public void receiveErrorsaveGradingSchemas(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for initializeGradebookWS method
            * override this method for handling normal response from initializeGradebookWS operation
            */
           public void receiveResultinitializeGradebookWS(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.InitializeGradebookWSResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from initializeGradebookWS operation
           */
            public void receiveErrorinitializeGradebookWS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteColumns method
            * override this method for handling normal response from deleteColumns operation
            */
           public void receiveResultdeleteColumns(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.DeleteColumnsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteColumns operation
           */
            public void receiveErrordeleteColumns(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateColumnAttribute method
            * override this method for handling normal response from updateColumnAttribute operation
            */
           public void receiveResultupdateColumnAttribute(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.UpdateColumnAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateColumnAttribute operation
           */
            public void receiveErrorupdateColumnAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGrades method
            * override this method for handling normal response from getGrades operation
            */
           public void receiveResultgetGrades(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetGradesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGrades operation
           */
            public void receiveErrorgetGrades(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAttempts method
            * override this method for handling normal response from getAttempts operation
            */
           public void receiveResultgetAttempts(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetAttemptsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAttempts operation
           */
            public void receiveErrorgetAttempts(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveGradebookTypes method
            * override this method for handling normal response from saveGradebookTypes operation
            */
           public void receiveResultsaveGradebookTypes(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.SaveGradebookTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveGradebookTypes operation
           */
            public void receiveErrorsaveGradebookTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRequiredEntitlements method
            * override this method for handling normal response from getRequiredEntitlements operation
            */
           public void receiveResultgetRequiredEntitlements(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetRequiredEntitlementsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRequiredEntitlements operation
           */
            public void receiveErrorgetRequiredEntitlements(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGrades method
            * override this method for handling normal response from deleteGrades operation
            */
           public void receiveResultdeleteGrades(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.DeleteGradesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGrades operation
           */
            public void receiveErrordeleteGrades(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveGrades method
            * override this method for handling normal response from saveGrades operation
            */
           public void receiveResultsaveGrades(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.SaveGradesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveGrades operation
           */
            public void receiveErrorsaveGrades(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGradingSchemas method
            * override this method for handling normal response from deleteGradingSchemas operation
            */
           public void receiveResultdeleteGradingSchemas(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.DeleteGradingSchemasResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGradingSchemas operation
           */
            public void receiveErrordeleteGradingSchemas(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGradebookTypes method
            * override this method for handling normal response from deleteGradebookTypes operation
            */
           public void receiveResultdeleteGradebookTypes(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.DeleteGradebookTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGradebookTypes operation
           */
            public void receiveErrordeleteGradebookTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveColumns method
            * override this method for handling normal response from saveColumns operation
            */
           public void receiveResultsaveColumns(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.SaveColumnsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveColumns operation
           */
            public void receiveErrorsaveColumns(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServerVersion method
            * override this method for handling normal response from getServerVersion operation
            */
           public void receiveResultgetServerVersion(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetServerVersionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServerVersion operation
           */
            public void receiveErrorgetServerVersion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGradebookColumns method
            * override this method for handling normal response from getGradebookColumns operation
            */
           public void receiveResultgetGradebookColumns(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetGradebookColumnsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGradebookColumns operation
           */
            public void receiveErrorgetGradebookColumns(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGradingSchemas method
            * override this method for handling normal response from getGradingSchemas operation
            */
           public void receiveResultgetGradingSchemas(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetGradingSchemasResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGradingSchemas operation
           */
            public void receiveErrorgetGradingSchemas(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteAttempts method
            * override this method for handling normal response from deleteAttempts operation
            */
           public void receiveResultdeleteAttempts(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.DeleteAttemptsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteAttempts operation
           */
            public void receiveErrordeleteAttempts(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveAttempts method
            * override this method for handling normal response from saveAttempts operation
            */
           public void receiveResultsaveAttempts(
                    com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.SaveAttemptsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveAttempts operation
           */
            public void receiveErrorsaveAttempts(java.lang.Exception e) {
            }
                


    }
    