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
    <p>This St. Lawrence College's electric vehicle charging stations web service endpoint. All resources can be accessed through GET requests made to the URI endpoints listed below. 
    <div>
      <h3>Devices</h3>
      <p>To obtain device names and ids request can be made to the devices resource.</p>
      <p>Parameters</p>
      <div id='deviceParamsTable'></div>
      <p><strong>Request:</strong></p>
      <pre><code> GET /api/devices </code></pre>
      <p><strong>Example Response:</strong></p>
      <div id="devicejsonoutput"></div>
    </div>
    <div>
      <h3>Data</h3>
      <p>To obtain the power readings for device requests can be made the devices/data resource.</p>
      <p>Parameters</p>
      <div id='dataParamsTable'></div>
      <p><strong>Request:</strong></p>
      <pre><code> GET /api/devices/data </code></pre>
      <p><strong>Example Response:</strong></p>
      <div id="datajsonoutput"></div>
    </div>
    <div>
      <h3>Instantaneous Data</h3>
      <p>To obtain the current power readings for device requests can be made the devices/instdata resource.</p>
      <p>Parameters</p>
      <div id='instDataParamsTable'></div>
      <p><strong>Request:</strong></p>
      <pre><code> GET /api/devices/instdata </code></pre>
      <p><strong>Example Response:</strong></p>
      <div id="instdatajsonoutput"></div>
    </div>
    
  </body>
</html>
