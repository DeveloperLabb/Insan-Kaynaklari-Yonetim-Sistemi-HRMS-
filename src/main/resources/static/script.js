function deleteChildElements(parentElement) {
    while (parentElement.firstChild) {
        parentElement.removeChild(parentElement.firstChild);
    }
}
function getPersons(){
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("getPersons").addEventListener("click", function() {
            fetch('http://localhost:8080/api/v1/person/getAllPerson', {
                method: 'GET'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok ' + response.statusText);
                    }
                    return response.json();
                })
                .then(data => {
                    // JSON dizisini işleme
                    const jsonArray = data;
                    // `responseData` öğesini seçme
                    const responseDataElement = document.getElementById("responseData");
                    // Liste oluşturma
                    deleteChildElements(responseDataElement);
                    const ul = document.createElement('ul');
                    jsonArray.forEach(person => {
                        const li = document.createElement('li');
                        li.textContent = `Name: ${person.name}, id: ${person.id}`;
                        ul.appendChild(li);
                    });
                    // Listeyi `responseData` öğesine ekleme
                    responseDataElement.appendChild(ul);

                })
                .catch(error => {
                    console.error('Error:', error); // Hata mesajını konsola yazdır
                });
        });
    });
}
getPersons();
function addPerson() {
    // Input elemanını seçme
    var nameInput = document.getElementById('name');
    // Input'un değerini alma
    var nameValue = nameInput.value;

    // Input'un boş olup olmadığını kontrol etme
    if (nameValue.trim() === "") {
        alert('Name cannot be blank');
        return; // Boşsa işlemi durdur
    }
    // Fetch ile POST isteği gönderme
    fetch('http://localhost:8080/api/v1/person/addPerson', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // Gönderilen verinin formatını belirtir
        },
        body: JSON.stringify({ name: nameValue }) // JSON formatında veri gönderir
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // Veriyi işleyin
            alert('Person added successfully');
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
