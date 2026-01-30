var i18nModule = (function(){
	'use strict';

	/**
     *  Naming convenctions
     * _privateVariable
     * _privateFunction
     * publicFunction
     * $varName jQuery object
     */
	
	var initModule = function (s) {
		if (typeof(i18n)!='undefined' && i18n[s]) {
			return i18n[s];
		}
		return s;
	}
	
	return {
		_: initModule 
	}
})();

