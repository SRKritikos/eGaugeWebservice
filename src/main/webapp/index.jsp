<%-- 
    Document   : index
    Created on : Jan 26, 2017, 9:35:10 PM
    Author     : Steven
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <script src="https://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script src="js/devicedata.json"></script>
    <script src="js/apiclient.js"></script>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/nvd3/1.8.4/nv.d3.min.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <style>
      body {
        width: 60%;
        margin: auto;
    </style>
  </head>
  <body>
    <h1>SLC EGauge WebService</h1>
    <div id="devicejsonoutput"></div>
    <div id="datajsonoutput"></div>
    <div id="instdatajsonoutput"></div>
  </body>
</html>
