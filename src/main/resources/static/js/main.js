
//**** Simulator form control functions ****

function toggleProcessId() {
	let id = document.getElementById("testType").value;
    if (id == "Quick") {
 		
 		sameCassetteCheckbox.checked = false;
 		   
    	hideDivs([
	    	"processIdDiv",
	    	"sameCassetteCheckboxDiv",
	    	"sameCassetteOptionsDiv"
    	]);
     
    } else if (id == "Timed") {
    
    	showDivs ([
	    	"processIdDiv",
	    	"sameCassetteCheckboxDiv",
    	])
    }
}

function showSameCassette() {
		let chBox = document.getElementById("sameCassetteCheckbox");
		sameCassetteOptionsDiv.style.display = chBox.checked ? "block" : "none";

		showDivs ([
			"processIdDivSame"
		]);
}


function selectedCassetteType() {

		let codeCassetteType = document.getElementById("cassetteTypeId").value;
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
	    	
   		}
   		
   		toggleProcessId();
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
					continue;
				}
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
					continue;
				}
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


