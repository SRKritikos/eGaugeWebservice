/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
  showDevices()
  showDeviceData()
  showDeviceInstData()
  dataparams = ["startDate", "endDate", "campus" ];
  datadesc = ["Starting date of data lookup. Formated as 'yyyy-MM-dd HH:mm:ss'", "Ending date of data lookup. Formated as 'yyyy-MM-dd HH:mm:ss'", "Name of campus for retrieved data" ];
  datadefaults = ["Current day and time", "90 days before startDate parameter", "All devices"];
  buildParametersTable(dataparams, datadesc, datadefaults, $("#dataParamsTable"));
  instdataparam = ["campus"];
  instdatadesc = ["Name of campus for retrieved device data"];
  instdatadefaults = ["All devices"];
  buildParametersTable(instdataparam, instdatadesc, instdatadefaults, $("#instDataParamsTable"));
  buildParametersTable(instdataparam, instdatadesc, instdatadefaults, $("#deviceParamsTable"));
});

function showDevices() {
  $out = $("#devicejsonoutput");
  outputJson($out, devices);
}

function showDeviceData() {
  $out = $("#datajsonoutput");
  outputJson($out, devicedata);
}

function showDeviceInstData() {
  $out = $("#instdatajsonoutput");
  outputJson($out, deviceinstdata);
}

function outputJson(element, data) {
  var outputData = JSON.stringify(data, null, 4);
  var $code = $("<pre></pre>").html(outputData);
  element.append($code);
}

function buildParametersTable(parameters, descriptions, defaults, target) {
  var $table = $("<table></table>").addClass("table table-striped");
  var $header = $("<tr><th>Parameter</th><th>Description</th><th>Default</th></tr>").appendTo($table);
  parameters.forEach(function(e,i){
    var $row = $("<tr></tr>");
    var $params = $("<td></td>").html(e).appendTo($row);
    var $description = $("<td></td>").html(descriptions[i]).appendTo($row);
    var $defaults = $("<td></td>").html(defaults[i]).appendTo($row);
    $row.appendTo($table);
  });
  $table.appendTo(target);
}


devicedata = {
    "devices": [
        {
            "deviceId": "1a42990f-8f22-4212-9c71-f86ae89d5af1",
            "deviceName": "Cornwall_Power",
            "deviceData": [
                {
                    "totalPower": 881485166,
                    "instPower": 3,
                    "timeRecorded": "Jan 28, 2017 7:01:28 PM"
                },
                {
                    "totalPower": 881485300,
                    "instPower": 5,
                    "timeRecorded": "Jan 28, 2017 7:00:58 PM"
                }
            ]
        },
        {
            "deviceId": "ed28534b-9fee-423d-bac5-57670e71f6c2",
            "deviceName": "Kingston_TotalPower",
            "deviceData": [
                {
                    "totalPower": 26110363434,
                    "instPower": 13,
                    "timeRecorded": "Jan 28, 2017 7:01:28 PM"
                },
                {
                    "totalPower": 26110363067,
                    "instPower": 13,
                    "timeRecorded": "Jan 28, 2017 7:00:58 PM"
                }
            ]
        },
        {
            "deviceId": "7e4d9817-9141-4b3d-8fa4-c1b5b590a050",
            "deviceName": "Kingston_Wand1_Power",
            "deviceData": [
                {
                    "totalPower": 12304267810,
                    "instPower": 9,
                    "timeRecorded": "Jan 28, 2017 7:01:28 PM"
                },
                {
                    "totalPower": 12304267547,
                    "instPower": 9,
                    "timeRecorded": "Jan 28, 2017 7:00:58 PM"
                }
            ]
        },
        {
            "deviceId": "fc60a7bc-4cff-4ea3-816e-57e86a6ee43c",
            "deviceName": "Kingston_Wand2_Power",
            "deviceData": [
                {
                    "totalPower": 13806095624,
                    "instPower": 4,
                    "timeRecorded": "Jan 28, 2017 7:01:28 PM"
                },
                {
                    "totalPower": 13806095520,
                    "instPower": 4,
                    "timeRecorded": "Jan 28, 2017 7:00:58 PM"
                }
            ]
        },
        {
            "deviceId": "822073b6-2e40-45a2-bbb9-551768eb86e6",
            "deviceName": "Brockville_Power",
            "deviceData": [
                {
                    "totalPower": 4328558052,
                    "instPower": 5,
                    "timeRecorded": "Jan 28, 2017 7:01:28 PM"
                },
                {
                    "totalPower": 4328557907,
                    "instPower": 5,
                    "timeRecorded": "Jan 28, 2017 7:00:58 PM"
                }
            ]
        }
    ]
};


devices = [
    {
        "deviceId": "7e4d9817-9141-4b3d-8fa4-c1b5b590a050",
        "deviceName": "Kingston_Wand1_Power",
    },
    {
        "deviceId": "fc60a7bc-4cff-4ea3-816e-57e86a6ee43c",
        "deviceName": "Kingston_Wand2_Power",
    },
    {
        "deviceId": "ed28534b-9fee-423d-bac5-57670e71f6c2",
        "deviceName": "Kingston_TotalPower",
    },
    {
        "deviceId": "1a42990f-8f22-4212-9c71-f86ae89d5af1",
        "deviceName": "Cornwall_Power",
    },
    {
        "deviceId": "822073b6-2e40-45a2-bbb9-551768eb86e6",
        "deviceName": "Brockville_Power",
    }
];

deviceinstdata = {
    "devices": [
        {
            "deviceName": "Cornwall_Power",
            "instPower": 2
        },
        {
            "deviceName": "Kingston_TotalPower",
            "instPower": 11
        },
        {
            "deviceName": "Kingston_Wand1_Power",
            "instPower": 8
        },
        {
            "deviceName": "Kingston_Wand2_Power",
            "instPower": 3
        },
        {
            "deviceName": "Brockville_Power",
            "instPower": 5
        }
    ]
};




