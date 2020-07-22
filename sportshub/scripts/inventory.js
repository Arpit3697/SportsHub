$(document).ready(
		function() {
			$.ajax({
				url : "InventoryChart",
				dataType : "JSON",
				success : function(result) {
					google.charts.load('current', {
						packages : [ 'corechart' ]
					});
					google.charts.setOnLoadCallback(function() {
						drawChart(result);
					});
				}
			});

			function drawChart(result) {
				var data = new google.visualization.DataTable();
				data.addColumn('string', 'eventname');
				data.addColumn('number', 'quantity');
				var dataArray = [];
				$.each(result, function(i, obj) {
					dataArray.push([ obj.eventname, obj.quantity ]);
				});

				data.addRows(dataArray);
					
				var barchart_options = {
					'title' : 'Inventory',
					'height' : '1000',
					'width' : '100%',
					'bars' : 'vertical',
					'bar' : {
						'groupWidth' : '80%'
					},
					'orientation' : 'vertical',
					'explorer' : {
						'axis' : 'vertical'
					},
					'animation' : {
						'startup' : 'true',
						'duration' : '1000'
					}
				};

				var barchart = new google.visualization.BarChart(document
						.getElementById('barchart_div'));
				barchart.draw(data, barchart_options);
			}
		});