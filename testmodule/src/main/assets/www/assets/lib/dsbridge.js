function getJsBridge() {
  window._dsf = window._dsf || {};
  return {
    call: function (b, a, c) {
      "function" == typeof a && (c = a, a = {});
      if ("function" == typeof c) {
        window.dscb = window.dscb || 0;
        var d = "dscb" + window.dscb++;
        window[d] = c;
        a._dscbstub = d
      }
      a = JSON.stringify(a || {});
      return window._dswk ? prompt(window._dswk + b, a) : "function" == typeof _dsbridge ? _dsbridge(b, a) : _dsbridge.call(b, a)
    }, register: function (b, a) {
      "object" == typeof b ? Object.assign(window._dsf, b) : window._dsf[b] = a
    }
  }
}
dsBridge = getJsBridge();
