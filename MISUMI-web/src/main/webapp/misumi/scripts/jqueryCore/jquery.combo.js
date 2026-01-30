(function( $ ) {
		$.widget( "ui.combobox", {
			options: {
				customAppendTo : null,
			},
			_create: function() {
				var self = this,
					select = this.element.hide(),
					selected = select.children( ":selected" ),
					value = selected.val() ? selected.text() : "";
				var input = this.input = $( "<input>" )
					.insertAfter( select )
					.val( value )
					.autocomplete({
						//appendTo: self.options.customAppendTo,
						appendTo: (self.options.customAppendTo || self.element.parent()),
						delay: 0,
						minLength: 0,
						source: function( request, response ) {
							var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
							response( select.children( "option" ).map(function() {
								var text = $( this ).text();
								if ( this.value && ( !request.term || matcher.test(text) ) )
									return {
										label: text.replace(
											new RegExp(
												"(?![^&;]+;)(?!<[^<>]*)(" +
												$.ui.autocomplete.escapeRegex(request.term) +
												")(?![^<>]*>)(?![^&;]+;)", "gi"
											), "<strong>$1</strong>" ),
										value: text,
										option: this
									};
							}) );
						},
//						response:function(event, ui ){
//							var kaka="";
//							
//						},
						select: function( event, ui ) {
							ui.item.option.selected = true;
							self._trigger( "selected", event, {
								item: ui.item.option
							});
						},
						change: function( event, ui ) {
							if ( !ui.item ) {
								var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
									valid = false;
								select.children( "option" ).each(function() {
									if ( $( this ).text().match( matcher ) ) {
										this.selected = valid = true;
										return false;
									}
								});
								if (ui.item==null){
									valid=false;
								}
								if ( !valid ) {
									// remove invalid value, as it didn't match anything
									$( this ).val( "" );
									select.val( "" );
									input.data( "autocomplete" ).term = "";
									self._trigger("changed", event, {
									    item: ui.item != null? ui.item.option : null,
									    value: $(this).val()
									});
									return false;
								}
							}
						}
					})
					.addClass( "ui-widget ui-widget-content ui-corner-left" );

				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a class='"+item.option.className+"'>" + item.label + "</a>" )
						.appendTo( ul );
				};

				this.button = $( "<button type='button'>&nbsp;</button>" )
					.attr( "tabIndex", -1 )
					.attr( "title", "Show All Items" )
					.insertAfter( input )
					.button({
						icons: {
							primary: "ui-icon-triangle-1-s"
						},
						text: false
					})
					.removeClass( "ui-corner-all" )
					.addClass( "ui-corner-right ui-button-icon" )
					.click(function() {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}

						// work around a bug (likely same cause as #5265)
						$( this ).blur();

						// pass empty string as value to search for, displaying all results
						input.autocomplete( "search", "" );
						input.focus();
					});
			},

			destroy: function() {
				this.input.remove();
				this.button.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			},
			
			autocomplete : function(value) {  
				  // this.element.val(value);  
				   this.input.val(value);  

			} ,
			
			getValue : function() {  
				  // this.element.val(value);  
				if (this.input.val()==null || this.input.val()==""){
					 this.element.val(null);
					return null;
				}else{
					return  this.element.val();
				}
				 

			},
			comboautocomplete : function(value) {  
				   this.element.val(value);  

			}, disable: function() {
	            this.button.button("disable");
	            this.input.autocomplete("disable"); // this disables just the popup menu (widget),
	            this.input.attr( "disabled", true );  // so disable the text input too...
	            this.input.addClass("ui-autocomplete-disabled ui-state-disabled").attr( "aria-disabled", true );
	        }, enable: function() {
            this.button.button("enable");
            this.input.autocomplete("enable");
            this.input.attr( "disabled", false );
            this.input.removeClass("ui-autocomplete-disabled ui-state-disabled").attr( "aria-disabled", false );
        }
				
		});
	})( jQuery );

