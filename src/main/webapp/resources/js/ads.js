$(document).ready(function() {	
	$(window).keydown(function(event){
		if(event.keyCode == 13) {
			event.preventDefault();
			return false;
		}
	});
	
	$('#contenttypeid').on('change', function(){
		var selectedOption = $(this).val();
		if(selectedOption === '1') {
			$('#fileContent').hide();
			$('#textContent').show();
		} else {
			$('#textContent').hide();
			$('#fileContent').show();
		}
	});
	
    $('#datetimepicker1').datepicker();
    
    $('#ads-table').DataTable();
});