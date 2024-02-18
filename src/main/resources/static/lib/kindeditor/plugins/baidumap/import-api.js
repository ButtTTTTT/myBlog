(function(){
    function getParam(name) {
		return location.href.match(new RegExp('[?&]' + name + '=([^?&]+)', 'i')) ? decodeURIComponent(RegExp.$1) : '';
	}
    var ak = getParam('ak');
    var apiSrc = '//api.map.baidu.com/api?v=2.0&ak=' + ak;
    document.write('<script type="text/javascript" src="' + apiSrc + '"></script>');
})();
