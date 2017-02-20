function sendToken(id_token, email_registro) {
  console.log("send Google Token");
  var email = email_registro
  $.ajax({
      type: "POST",
      url: hostURL + 'api/authentication/googleSingIn',
      contentType: "text/plain",
      data: id_token,
      success: function(data) {
          console.log("success login google");
          data.email = email;
          console.log(data);
          sessionStorage.setItem('login', JSON.stringify(data));
          window.location.href = "inscripciones.html";
      },
      error: function(jqXHR, textStatus, errorThrown) {
          console.log("Error login Google");
          console.log(errorThrown);
          console.log(jqXHR.responseText);
          $("#mensaje").text("Error en el servidor");
      }
  });
}

function onSuccess(googleUser) {
    var id_token = googleUser.getAuthResponse().id_token;
    var profile = googleUser.getBasicProfile();
    console.log('token: ' + id_token); // Do not send to your backend! Use an ID token instead.
    console.log('Email: ' + profile.getEmail());
    sendToken(id_token, profile.getEmail());
}

function onFailure(error) {
    console.log(error);
}

function renderButton() {
    console.log("renderButton()")
    gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 240,
        'height': 50,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
    });
}


function Escalador(fname, lname, age, eyecolor) {
    this.nombre = fname;
    this.apellido = lname;
    this.email = age;
    this.password = eyecolor;
}

function Credenciales(username, password) {
    this.username = username;
    this.password = password;
}

function Token(token) {
    this.token = token;
}
