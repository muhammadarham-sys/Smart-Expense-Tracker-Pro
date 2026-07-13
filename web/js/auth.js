async function registerUser() {

    let username =
        document.getElementById("username").value;

    let password =
        document.getElementById("password").value;

    const response = await fetch(
        "http://localhost:8080/api/auth/register",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        }
    );

    const result = await response.text();

    alert(result);
}

async function login() {

    let username =
        document.getElementById("username").value;

    let password =
        document.getElementById("password").value;

    const response = await fetch(
        "http://localhost:8080/api/auth/login",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        }
    );

    const result = await response.text();

    if(result === "Login Successful") {

        window.location.href =
            "dashboard.html";

    } else {

        alert(result);
    }
}