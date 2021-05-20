
//**** Simulator form control functions ****

function toggleProcessId() {
	let testType = document.getElementById("testType").value;
	let codeCassetteType = document.getElementById("cassetteTypeId").value;
	
    if (testType == "Quick") {
 		
 		sameCassetteCheckbox.checked = false;
 		
 		hideDivs([
	    	"processIdDiv",
	    	"processIdDivSame",
	    	"sameCassetteCheckboxDiv",
	    	"sameCassetteOptionsDiv"
    	]);
    	
    	switch (codeCassetteType){
    	
    	case "2":
    		hideDivs ([
				"singleTestDiv",
				"singleTestDivSame",
				"cplScanDiv",
				"cplScanDivSame", 
				"flex4TestDiv",
				"flex4TestDivSame",
				"felvFivTestDivSame"
			]);
			
			showDivs ([
				"felvFivTestDiv"
			]);
    	break;
    	
    	case "8":
    		hideDivs ([
				"singleTestDiv",
				"singleTestDivSame",
				"felvFivTestDiv",
				"felvFivTestDivSame",
				"flex4TestDiv",
				"flex4TestDivSame",
				"cplScanDivSame"
			]);
			
			showDivs ([
				"singleTestDiv",
				"cplScanDiv"
			]);
    	break;
    	
    	case "9":
    		hideDivs ([
				"singleTestDiv",
				"singleTestDivSame",
				"felvFivTestDiv",
				"felvFivTestDivSame",
				"cplScanDiv",
				"cplScanDivSame",
				"flex4TestDivSame",
			]);
			
			showDivs ([
				"flex4TestDiv"
			]);
    	
    	break;
    	
    	default:
    		hideDivs ([
				"felvFivTestDiv",
				"felvFivTestDivSame",
				"cplScanDiv",
				"cplScanDivSame",
				"flex4TestDiv",
				"flex4TestDivSame"
			]);
			
			showDivs ([
				"singleTestDiv"
			]);
    	
    	}
    	
    
    } else if (testType == "Timed") {
    
    	showDivs ([
	    	"processIdDiv",
	    	"sameCassetteCheckboxDiv"
    	])
    
    	switch (codeCassetteType) {
    	
    		case "2":
    			showDivs ([
					"felvFivTestDiv",
				]);
    		break;
    	
	    	case "8":
    			showDivs ([
					"singleTestDiv",
					"cplScanDiv",
				]);
	    	break;
    	
    		case "9":
	    		showDivs ([
					"flex4TestDiv"
				]);
    		break;
    	
    		default:
		    	showDivs ([
					"singleTestDiv"
				]);
    	}	
    }
}

function showSameCassette() {
		let chBox = document.getElementById("sameCassetteCheckbox");
		let codeCassetteType = document.getElementById("cassetteTypeId").value;
		
		if(chBox.checked){
			
			showDivs ([
				"sameCassetteOptionsDiv",
				"processIdDivSame"
			]);
			
			switch (codeCassetteType) {
    	
  		  		case "2":
    				showDivs ([
						"felvFivTestDiv",
						"felvFivTestDivSame"
					]);
					
					hideDivs ([
						"singleTestDiv",
						"singleTestDivSame",
						"cplScanDiv",
						"cplScanDivSame",
						"flex4TestDiv",
						"flex4TestDivSame"
					])
    			break;
    	
    			case "8":
    				showDivs ([
						"singleTestDiv",
						"singleTestDivSame",
						"cplScanDiv",
						"cplScanDivSame"
					]);
					
					hideDivs ([
						"felvFivTestDiv",
						"felvFivTestDivSame",
						"flex4TestDiv",
						"flex4TestDivSame"
					])
	    		break;
    	
    			case "9":
    				showDivs ([
						"flex4TestDiv",
						"flex4TestDivSame"
					]);
					
					hideDivs ([
						"singleTestDiv",
						"singleTestDivSame",
						"felvFivTestDiv",
						"felvFivTestDivSame",
						"cplScanDiv",
						"cplScanDivSame"
					])
    			break;
    	
    			default:
    				showDivs ([
						"singleTestDiv",
						"singleTestDivSame"
					]);
					
					hideDivs ([
						"felvFivTestDiv",
						"felvFivTestDivSame",
						"cplScanDiv",
						"cplScanDivSame",
						"flex4TestDiv",
						"flex4TestDivSame"
					])
					
    		}	
			
		} else {
			
			hideDivs ([
				"sameCassetteOptionsDiv",
				"processIdDivSame"
			]);
		}		
}


function selectedCassetteType() {

		let codeCassetteType = document.getElementById("cassetteTypeId").value;
		let chBox = document.getElementById("sameCassetteCheckbox");
		
		switch (codeCassetteType){
		
			//------------Felv Fiv status ------------------
	
			case "2": 
				if (chBox.checked){
				
					hideDivs ([
						"singleTestDiv",
						"singleTestDivSame", 
						"cplScanDiv", 
						"cplScanDivSame",
						"flex4TestDiv",
						"flex4TestDivSame"
					]);
					
					showDivs ([
						"felvFivTestDiv",
						"felvFivTestDivSame",
						"sameCassetteOptionsDiv"
					]);
					
				} else if (!chBox.checked) {
				
					hideDivs ([
						"sameCassetteOptionsDiv",
						"singleTestDiv",
						"singleTestDivSame", 
						"cplScanDiv",
						"cplScanDivSame", 
						"flex4TestDiv",
						"flex4TestDivSame",
						"felvFivTestDivSame"
					]);
					
					showDivs ([
						"felvFivTestDiv"
					]);
					
				}	
				break;
			
			//------------cPL status ------------------		
			
			case "8":
				if (chBox.checked){
					hideDivs ([
						"felvFivTestDiv",
						"felvFivTestDivSame",
						"flex4TestDiv",
						"flex4TestDivSame"
					])
					
					showDivs ([
						"sameCassetteOptionsDiv",
						"singleTestDiv",
						"singleTestDivSame",
						"cplScanDiv",
						"cplScanDivSame"
					]);
					
				} else if (!chBox.checked) {
					
					hideDivs ([
						"sameCassetteOptionsDiv",
						"felvFivTestDiv",
						"felvFivTestDivSame",
						"flex4TestDiv",
						"flex4TestDivSame",
						"cplScanDivSame"
					]);
					
					showDivs ([
						"singleTestDiv",
						"cplScanDiv"
					]);
					
				}
		    	break;
	    	
			//-------- Flex4 status ----------------------		
	    	
	    	case "9": 
	    		if (chBox.checked){
	    		
	    			hideDivs ([
			    		"singleTestDiv",
			    		"singleTestDivSame", 
			    		"felvFivTestDiv",
			    		"felvFivTestDivSame",
			    		"cplScanDiv",
			    		"cplScanDivSame"
		    		]);
	    		
	    			showDivs ([
	    				"sameCassetteOptionsDiv",
						"flex4TestDiv",
						"flex4TestDivSame"
					]); 
	    		
	    		
	    		} else if (!chBox.checked) {
	    		
	    			hideDivs ([
			    		"singleTestDiv",
			    		"singleTestDivSame", 
			    		"felvFivTestDiv",
			    		"felvFivTestDivSame",
			    		"cplScanDiv",
			    		"cplScanDivSame",
			    		"flex4TestDivSame"
		    		]);
	    		
	    			showDivs ([
						"flex4TestDiv",
					]); 
	    		
	    		}
		     	break;
	     	
	 		//-------- Default status for all cassettes except cpL, Felv Fiv & Flex4 -------		
	     	
	     	default:
	     	
	     		if (chBox.checked){
	     		
		     		hideDivs ([
			     		"felvFivTestDiv",
			     		"felvFivTestDivSame",
			     		"flex4TestDiv",
			     		"flex4TestDivSame",
			     		"cplScanDiv",
			     		"cplScanDivSame"
		     		]);
		     		
		     		showDivs ([
			     		"singleTestDiv",
			     		"singleTestDivSame",
			     		"sameCassetteOptionsDiv"
		     		]);
	     		
	     		
	     		} else if (!chBox.checked) {
	     		
	     			hideDivs ([
	     				"sameCassetteOptionsDiv",
			     		"felvFivTestDiv",
			     		"felvFivTestDivSame",
			     		"flex4TestDiv",
			     		"flex4TestDivSame",
			     		"cplScanDiv",
			     		"cplScanDivSame",
			     		"singleTestDivSame"
		     		]);
		     		
		     		showDivs ([
			     		"singleTestDiv",
		     		]);
	     		}
	     		
   		}
   		
//  		toggleProcessId(); TODO: Revisar si es pot optimitzar encadenant crides a funcions
}

// ------ Status functions

function showDivs (visibleDivs){
		for (let elementDiv	of visibleDivs){
			const toVisibleDiv=document.getElementById(elementDiv);
			toVisibleDiv.style.display="block";
			let inputNodes=toVisibleDiv.getElementsByTagName("input");
			for (let node of inputNodes){
				if (node.classList.contains("never-required") || node.getAttribute("type")=="checkbox" || node.getAttribute("type")=="hidden"){
					continue;
				}
				node.required=true;
			}
		}
}

function hideDivs (hiddenDivs) {
		for (let elementDiv	of hiddenDivs){
			const toHiddenDiv=document.getElementById(elementDiv);
			toHiddenDiv.style.display="none";
			let inputNodes=toHiddenDiv.getElementsByTagName("input");
			for (let node of inputNodes){
				if (node.getAttribute("type")=="checkbox" || node.getAttribute("type")=="hidden"){
					continue;
				}
				node.removeAttribute("required");
				node.setAttribute("value","");
			}
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


