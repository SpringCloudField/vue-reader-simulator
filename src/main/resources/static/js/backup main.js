

function toggleProcessId(id) {
    if (id == "Quick") {
 		   
    	hideDivs([
    	"processIdDiv",
    	"sameCassetteCheck", 
    	"processIdDivSame"
    	]);
		
		setNotRequiredInputFields ([
		"processIdInput",
		"previousProcessIdInput",
		"processIdInputSame",
		"previousProcessIdInputSame"
		]);
    
//    	processIdDiv.style.display = "none";
//    	processIdInput.required = false;
//    	previousProcessIdInput.required = false;
//    	sameCassetteCheck.style.display = "none";
//
//    	processIdDivSame.style.display = "none";
//    	processIdInputSame.required = false;
//    	previousProcessIdInputSame.required = false;
    	
 //   	document.getElementById("processIdDiv").style.display = "none";
 //   	document.getElementById("processIdInput").required = false;
 //   	document.getElementById("previousProcessIdInput").required = false;
 
    } else if (id == "Timed") {
    
    	showDivs ([
    	"processIdDiv",
    	"sameCassetteCheck",
    	"processIdDivSame"
    	])
    	
    	setRequiredInputFields ([
    	"processIdInput",
    	"previousProcessIdInput"
    	])
    	
//  		processIdDiv.style.display = "block";  
//    	processIdInput.required = true;
//    	previousProcessIdInput.required = true;
//    	sameCassetteCheck.style.display = "block";
//   
//    	processIdDivSame.style.display = "block";
//    	processIdInputSame.required = true;
//    	previousProcessIdInputSame.required = true;
//    
 //  	document.getElementById("processIdDiv").style.display = "block";
 //   	document.getElementById("processIdInput").required = true;
 //   	document.getElementById("previousProcessIdInput").required = true;

    }
}

function showSameCassette(chBox) {	

//	const sameCassetteCheckDiv = document.getElementById("sameCassetteCheckDiv");

	sameCassetteCheckDiv.style.display = chBox.checked ? "block" : "none";
	setRequiredInputFields ([
	"processIdInputSame",
	"previousProcessIdInputSame"
	])
	
//	var sameCassetteSelected = true;
}


function selectedCassetteType(codeCassetteType) {
	
		switch (codeCassetteType){
		
		case "1":
		singleTestDiv.style.display = "flex";
     	felvFivTestDiv.style.display = "none";
     	processIdDiv.style.display = "none";
     	
    	cplScanDiv.style.display = "none";
    	
		break;
		
		case "2": //felVfiV
		singleTestDiv.style.display = "none";
		felvFivTestDiv.style.display = "block";
		break;
		
		
		case "8": //cPL
		
//    if (codeCassetteType == "8") { 

		showDivs (["cplScanDiv","cplScanDivSame"])
//    	cplScanDiv.style.display = "block";
//    	cplScanDivSame.style.display = "block";
   
 		setRequiredInputFields (["lotNumberInput","scaledResultInput"]);  
 		
//    	lotNumberInput.required = true;
//    	scaledResultInput.required = true;
    	break;
    
//    	document.getElementById("cplScanDiv").style.display = "block";
//    	document.getElementById("lotNumberInput").required = true;
//    	document.getElementById("scaledResult").required = true;
//    } else {


     
		     
     default: 
     	singleTestDiv.style.display = "block";
     	processIdDiv.style.display = "none";
     	felvFivTestDiv.style.display = "none";
    	cplScanDiv.style.display = "none";
    	cplScanDivSame.style.display = "none";
    	
    	setNotRequiredInputFields ([lotNumberInput, scaledResultInput])
    	
//    	lotNumberInput.removeAttribute ("required");
//    	scaledResultInput.removeAttribute ("required"); 
    	
//    	document.getElementById("cplScanDiv").style.display = "none";
//    	document.getElementById("lotNumberInput").required = false;
//    	document.getElementById("scaledResult").required = false;
//    }
//    
//    if (codeCassetteType == "2") {
//    	
    }
    
}




function setNotRequiredInputFields (notRequiredFields){
		for (let elementInput of notRequiredFields){
		document.getElementById(elementInput).removeAttribute ("required");
		}
}

function setRequiredInputFields (requiredFields){
		for (let elementInput of requiredFields) {
		console.info(elementInput);
		document.getElementById(elementInput).required = true;
		}
}

function showDivs (visibleDivs){
		for (let elementDiv	of visibleDivs){
		document.getElementById(elementDiv).style.display="block";
		}
}

function hideDivs (hiddenDivs) {
		for (let elementDiv	of hiddenDivs){
		document.getElementById(elementDiv).style.display="none";
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