(function($) {
	jQuery.fn.parseNumber.defaults = {
		locale: "es",
		decimalSeparatorAlwaysShown: false,
		isPercentage: false,
		isFullLocale: false
	};

	jQuery.fn.formatNumber.defaults = {
		format: "0.###",
		locale: "es",
		decimalSeparatorAlwaysShown: false,
		nanForceZero: true,
		round: true,
		isFullLocale: false
	};
})(jQuery);
