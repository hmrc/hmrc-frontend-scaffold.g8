
window.GOVUKFrontend.initAll();
document.body.className = ((document.body.className) ? document.body.className + ' js-enabled' : 'js-enabled');

  // =====================================================
  // Back link mimics browser back functionality
  // =====================================================
  // store referrer value to cater for IE - https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/10474810/  */
const docReferrer = document.referrer
  // prevent resubmit warning
  if (window.history && window.history.replaceState && typeof window.history.replaceState === 'function') {
    window.history.replaceState(null, null, window.location.href);
  }
document.getElementById("back-link").addEventListener("click", function(e){
    e.preventDefault();
    if (window.history && window.history.back && typeof window.history.back === 'function' &&
       (docReferrer !== "" && docReferrer.indexOf(window.location.host) !== -1)) {
        window.history.back();
    }
  });
