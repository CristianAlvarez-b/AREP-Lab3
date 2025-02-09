document.getElementById("apiForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const method = document.getElementById("method").value;
    const url = `/app/hello`;

    let options = { method };

    if (method === "GET") {
        fetch(`${url}?name=${encodeURIComponent(name)}`)
            .then((response) => response.json())
            .then((data) => {
                document.getElementById("response").innerHTML = `<p>${data.message}</p>`;
            })
            .catch((error) => {
                document.getElementById("response").innerHTML = `<p>Error: ${error.message}</p>`;
            });
    } else {
        options.headers = { "Content-Type": "application/json" };
        options.body = JSON.stringify({ name });

        fetch(url, options)
            .then((response) => response.json())
            .then((data) => {
                document.getElementById("response").innerHTML = `<p>${data.message}</p>`;
            })
            .catch((error) => {
                document.getElementById("response").innerHTML = `<p>Error: ${error.message}</p>`;
            });
    }
});
