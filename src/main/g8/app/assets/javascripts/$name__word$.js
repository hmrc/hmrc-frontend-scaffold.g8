\$(document).ready(function() {

  // =====================================================
  // Initialise show-hide-content
  // Toggles additional content based on radio/checkbox input state
  // =====================================================
  var showHideContent, mediaQueryList;
  showHideContent = new GOVUK.ShowHideContent()
  showHideContent.init()

  // =====================================================
  // Use GOV.UK shim-links-with-button-role.js to trigger
  // links with role="button" when space key is pressed
  // =====================================================
  GOVUK.shimLinksWithButtonRole.init();

  // =====================================================
  // Handle number inputs
  // =====================================================
    numberInputs();

  // =====================================================
  // Back link mimics browser back functionality
  // =====================================================
  // store referrer value to cater for IE - https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/10474810/  */
  var docReferrer = document.referrer
  // prevent resubmit warning
  if (window.history && window.history.replaceState && typeof window.history.replaceState === 'function') {
    window.history.replaceState(null, null, window.location.href);
  }
  // back click handle, dependent upon presence of referrer & no host change
  \$('#back-link').on('click', function(e){
    e.preventDefault();
    if (window.history && window.history.back && typeof window.history.back === 'function' &&
       (docReferrer !== "" && docReferrer.indexOf(window.location.host) !== -1)) {
        window.history.back();
    }
  })

  //======================================================
  // Move immediate forcus to any error summary
  //======================================================
  if (\$('.error-summary a').length > 0){
    \$('.error-summary').focus();
  }

  // =====================================================
  // Adds data-focuses attribute to all containers of inputs listed in an error summary
  // This allows validatorFocus to bring viewport to correct scroll point
  // =====================================================
      function assignFocus () {
          var counter = 0;
          \$('.error-summary-list a').each(function(){
              var linkhash = \$(this).attr("href").split('#')[1];
              \$('#' + linkhash).parents('.form-field, .form-group').first().attr('id', 'f-' + counter);
              \$(this).attr('data-focuses', 'f-' + counter);
              counter++;
          });
      }
      assignFocus();

      function beforePrintCall(){
          if(\$('.no-details').length > 0){
              // store current focussed element to return focus to later
              var fe = document.activeElement;
              // store scroll position
              var scrollPos = window.pageYOffset;
              \$('details').not('.open').each(function(){
                  \$(this).addClass('print--open');
                  \$(this).find('summary').trigger('click');
              });
              // blur focus off current element in case original cannot take focus back
              \$(document.activeElement).blur();
              // return focus if possible
              \$(fe).focus();
              // return to scroll pos
              window.scrollTo(0,scrollPos);
          } else {
              \$('details').attr("open","open").addClass('print--open');
          }
          \$('details.print--open').find('summary').addClass('heading-medium');
      }

      function afterPrintCall(){
          \$('details.print--open').find('summary').removeClass('heading-medium');
          if(\$('.no-details').length > 0){
              // store current focussed element to return focus to later
              var fe = document.activeElement;
              // store scroll position
              var scrollPos = window.pageYOffset;
              \$('details.print--open').each(function(){
                  \$(this).removeClass('print--open');
                  \$(this).find('summary').trigger('click');
              });
              // blur focus off current element in case original cannot take focus back
              \$(document.activeElement).blur();
              // return focus if possible
              \$(fe).focus();
              // return to scroll pos
              window.scrollTo(0,scrollPos);
          } else {
              \$('details.print--open').removeAttr("open").removeClass('print--open');
          }
      }

      //Chrome
      if(typeof window.matchMedia != 'undefined'){
          mediaQueryList = window.matchMedia('print');
          mediaQueryList.addListener(function(mql) {
              if (mql.matches) {
                  beforePrintCall();
              };
              if (!mql.matches) {
                  afterPrintCall();
              };
          });
      }

      //Firefox and IE (above does not work)
      window.onbeforeprint = function(){
          beforePrintCall();
      }
      window.onafterprint = function(){
          afterPrintCall();
      }
  });


  function numberInputs() {
      // =====================================================
      // Set currency fields to number inputs on touch devices
      // this ensures on-screen keyboards display the correct style
      // don't do this for FF as it has issues with trailing zeroes
      // =====================================================
      if(\$('html.touchevents').length > 0 && window.navigator.userAgent.indexOf("Firefox") == -1){
          \$('[data-type="currency"] > input[type="text"], [data-type="percentage"] > input[type="text"]').each(function(){
            \$(this).attr('type', 'number');
            \$(this).attr('step', 'any'); 
            \$(this).attr('min', '0');
          });
      }

      // =====================================================
      // Disable mouse wheel and arrow keys (38,40) for number inputs to prevent mis-entry
      // also disable commas (188) as they will silently invalidate entry on Safari 10.0.3 and IE11
      // =====================================================
      \$("form").on("focus", "input[type=number]", function(e) {
          \$(this).on('wheel', function(e) {
              e.preventDefault();
          });
      });
      \$("form").on("blur", "input[type=number]", function(e) {
          \$(this).off('wheel');
      });
      \$("form").on("keydown", "input[type=number]", function(e) {
          if ( e.which == 38 || e.which == 40 || e.which == 188 )
              e.preventDefault();
      });
  }
