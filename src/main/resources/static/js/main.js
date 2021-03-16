function showSameCassette(chBox) {	
	var div = document.getElementById("sameCassetteDiv");
	div.style.display = chBox.checked ? "block" : "none";
}

function toggleProcessId(id) {
    if (id == "Quick") {
    	document.getElementById("processIdDiv").style.display = "none";
    	document.getElementById("processIdInput").required = false;
    	document.getElementById("prevousProcessIdInput").required = false;
    } else if (id == "Timed") {
    	document.getElementById("processIdDiv").style.display = "block";
    	document.getElementById("processIdInput").required = true;
    	document.getElementById("prevousProcessIdInput").required = true;
    }
}

function selectedCassetteType(codeCassetteType) {
    if (codeCassetteType == "8") {
    	document.getElementById("cplScanDiv").style.display = "block";
    	document.getElementById("lotNumberInput").required = true;
    	document.getElementById("scaledResult").required = true;
    } else {
    	document.getElementById("cplScanDiv").style.display = "none";
    	document.getElementById("lotNumberInput").required = false;
    	document.getElementById("scaledResult").required = false;
    }
}


// Self-executing function
(function() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();


$(document).ready(function () {

    $("#config-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    var search = {}

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/saveConfig",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}
