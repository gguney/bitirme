function getSearchSuggestion(e, suggest,page){
	var unicode = e.keyCode ? e.keyCode : e.charCode;
	if((unicode >= 65 && unicode <= 90) || unicode == 8){
		if(suggest != ""){
			$.get("./php/getSearchSuggestion.php?"+page, { search: suggest }, function(data){
				$('#suggestbox').html(data);
				if($('#suggestbox').is(":visible") == false){
					$('#suggestbox').show();
					//$('#search_input').css('border-bottom-left-radius', 0);
				}
			});
		}else{
			$('#suggestbox').html("").hide();
			//$('#search_input').css('border-bottom-left-radius', 5);
		}
	}
}
function getAnalyzeSuggestion(e, suggest,page,no){
	var unicode = e.keyCode ? e.keyCode : e.charCode;

	if((unicode >= 65 && unicode <= 90) || unicode == 8){
		
		if(suggest != ""){
			$.get("./php/getAnalyzeSuggestion.php?"+page, { search: suggest+"*"+no }, function(data){
				$('#suggestbox'+no).html(data);
				if($('#suggestbox'+no).is(":visible") == false){
					$('#suggestbox'+no).show();

					//$('#search_input'+no).css('border-bottom-left-radius', 0);
				}
			});
		}else{
			$('#suggestbox'+no).html("").hide();
			//$('#search_input'+no).css('border-bottom-left-radius', 5);
		}
	}
}
function putSuggestion(element){
	$('#search_input').val($(element).html()).css('border-bottom-left-radius', 5);
	$('#suggestbox').hide();
}
function putSuggestionMulti(element,no){
	$('#search_input'+no).val($(element).html()).css('border-bottom-left-radius', 5);
	$('#suggestbox'+no).hide();
}

function search(){
	var search_term = $('#search_input').val();
	window.location = 'search.php?term=' + search_term;
}