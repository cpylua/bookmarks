
/**
 * BookmarkServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */

    package client.bookmark.service;

    /**
     *  BookmarkServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class BookmarkServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public BookmarkServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public BookmarkServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for logout method
            * override this method for handling normal response from logout operation
            */
           public void receiveResultlogout(
                    client.bookmark.service.BookmarkServiceStub.LogoutResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from logout operation
           */
            public void receiveErrorlogout(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for regsiter method
            * override this method for handling normal response from regsiter operation
            */
           public void receiveResultregsiter(
                    client.bookmark.service.BookmarkServiceStub.RegsiterResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from regsiter operation
           */
            public void receiveErrorregsiter(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for login method
            * override this method for handling normal response from login operation
            */
           public void receiveResultlogin(
                    client.bookmark.service.BookmarkServiceStub.LoginResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from login operation
           */
            public void receiveErrorlogin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllBookmark method
            * override this method for handling normal response from getAllBookmark operation
            */
           public void receiveResultgetAllBookmark(
                    client.bookmark.service.BookmarkServiceStub.GetAllBookmarkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllBookmark operation
           */
            public void receiveErrorgetAllBookmark(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addBookmark method
            * override this method for handling normal response from addBookmark operation
            */
           public void receiveResultaddBookmark(
                    client.bookmark.service.BookmarkServiceStub.AddBookmarkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addBookmark operation
           */
            public void receiveErroraddBookmark(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeBookmark method
            * override this method for handling normal response from removeBookmark operation
            */
           public void receiveResultremoveBookmark(
                    client.bookmark.service.BookmarkServiceStub.RemoveBookmarkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeBookmark operation
           */
            public void receiveErrorremoveBookmark(java.lang.Exception e) {
            }
                


    }
    