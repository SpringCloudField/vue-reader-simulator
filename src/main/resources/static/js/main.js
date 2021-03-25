
//**** Simulator form control functions ****

function toggleProcessId(id) {
    if (id == "Quick") {
 		
 		sameCassetteCheckbox.checked = false;
 		   
    	hideDivs([
	    	"processIdDiv",
	    	"sameCassetteCheckboxDiv",
	    	"sameCassetteOptionsDiv"
    	]);
    	    	
//		setNotRequiredInputFields ([
//			"processIdInput",
//			"previousProcessIdInput",
//			"processIdInputSame",
//			"previousProcessIdInputSame"
//		]);
     
    } else if (id == "Timed") {
    
    	showDivs ([
	    	"processIdDiv",
	    	"sameCassetteCheckboxDiv",
    	])
    	
//    	setRequiredInputFields ([
//	    	"processIdInput",
//	    	"previousProcessIdInput"
//    	])
    }
}

function showSameCassette(chBox) {	
		sameCassetteOptionsDiv.style.display = chBox.checked ? "block" : "none";

		showDivs ([
			"processIdDivSame"
		]);
}


function selectedCassetteType(codeCassetteType) {
		switch (codeCassetteType){
		
//------------Felv Fiv status ------------------
	
		case "2": 
		
		hideDivs ([
			"singleTestDiv", 
			"cplScanDiv", 
			"flex4TestDiv", 
			"singleTestDivSame", 
			"cplScanDivSame", 
			"flex4TestDivSame"
		]);
		
		showDivs ([
			"felvFivTestDiv",
			"felvFivTestDivSame"
		]);
		
//		setRequiredInputFields ([
//			"felvControlInput",
//			"felvNoiseInput",
//			"felvTestLineValueInput"
//		])
		
		break;
		
//------------cPL status ------------------		
		
		case "8":
		
		hideDivs ([
			"felvFivTestDiv",
			"flex4TestDiv",
			"felvFivTestDivSame",
			"flex4TestDivSame"
		])
		
		showDivs ([
			"singleTestDiv",
			"cplScanDiv",
			"cplScanDivSame"
		]);
		
// 		setRequiredInputFields ([
//	 		"lotNumberInput",
//	 		"scaledResultInput"
// 		]);  
 		
    	break;
    	
//-------- Flex4 status ----------------------		
    	
    	case "9": 
    	
    	hideDivs ([
	    	"singleTestDiv",
	    	"singleTestDivSame", 
	    	"felvFivTestDiv",
	    	"felvFivTestDivSame",
	    	"cplScanDiv",
	    	"cplScanDivSame"
    	]);
		
		showDivs ([
			"flex4TestDiv",
			"flex4TestDivSame"
		]);    
		
     	break;
     	
 //-------- Default status for all cassettes except cpL, Felv Fiv & Flex4 -------		
     	
     	default:
     	
     	showDivs ([
	     	"singleTestDiv",
	     	"singleTestDivSame"
     	]);
     	
     	hideDivs ([
	     	"felvFivTestDiv",
	     	"felvFivTestDivSame",
	     	"flex4TestDiv",
	     	"flex4TestDivSame",
	     	"cplScanDiv",
	     	"cplScanDivSame"
     	]);
     	
//    	setNotRequiredInputFields ([
//	    	"lotNumberInput", 
//	    	"scaledResultInput"
//    	]);
    	
   		}
}

// ------ Status functions

function setNotRequiredInputFields (notRequiredFields){
		for (let elementInput of notRequiredFields){
			document.getElementById(elementInput).removeAttribute ("required");
		}
}

function setRequiredInputFields (requiredFields){
		for (let elementInput of requiredFields) {
			document.getElementById(elementInput).required = true;
		}
}

function showDivs (visibleDivs){
		for (let elementDiv	of visibleDivs){
			const toVisibleDiv=document.getElementById(elementDiv);
			toVisibleDiv.style.display="block";
			let inputNodes=toVisibleDiv.getElementsByTagName("input");
			console.info ("------- DIV SHOW:" + toVisibleDiv.getAttribute("id") + " -------"); 
			let requiredInputs=" --> ";
			for (let node of inputNodes){
				if (node.classList.contains("never-required") || node.getAttribute("type")=="checkbox" || node.getAttribute("type")=="hidden"){
				console.info ("   NOT SET REQUIRED: " + node.getAttribute("id") + " --> " + node.getAttribute("type")+ " ----> "  + node.classList);
				continue;}
				node.required=true;
				requiredInputs += " " + node.getAttribute("id"); 
			}
			console.info (" REQUIRED INPUTS: " + requiredInputs);
		}
}

function hideDivs (hiddenDivs) {
		for (let elementDiv	of hiddenDivs){
			const toHiddenDiv=document.getElementById(elementDiv);
			toHiddenDiv.style.display="none";
			let inputNodes=toHiddenDiv.getElementsByTagName("input");
			console.info ("------- DIV HIDE:" + toHiddenDiv.getAttribute("id") + " -------");
			let removedRequiredValueInputs=" --> " 
			for (let node of inputNodes){
				if (node.getAttribute("type")=="checkbox" || node.getAttribute("type")=="hidden"){
				console.info ("   NOT REMOVED REQUIRED/VALUE: " + node.getAttribute("id") + " --o " + node.getAttribute("type") + " ----o " + node.classList);
				continue;}
				node.removeAttribute("required");
				node.setAttribute("value","");
				removedRequiredValueInputs += " " + node.getAttribute("id");
			}
			console.info (" REMOVED REQUIRED & VALUE INPUTS: " + removedRequiredValueInputs);
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
