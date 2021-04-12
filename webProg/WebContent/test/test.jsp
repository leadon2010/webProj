<!DOCTYPE html>
<html>

<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
		$(function () {
			var t = evenSum(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			console.log(t);
			var od = oddSum(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13);
			console.log(od);
		})
		function oddSum() {
			//var ary = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
			var ary = [];
			for (x in arguments) {
				ary[x] = arguments[x];
			}
			var total = 0;
			ary.forEach(function (index, argument) {
				if (argument % 2 == 1) {
					total += argument;
				}
			})
			return total;
		}
		function evenSum() {
			var total = 0;
			$.each(arguments, function (i, o) {
				if (o % 2 == 0) {
					total += o;
				}
			})
			return total;
		}
	</script>
</head>

<body>

	<input type="text" value="text" />
	<button id="btn">Click</button>
	<div id="result">Here</div>
	<br>

</body>

</html>