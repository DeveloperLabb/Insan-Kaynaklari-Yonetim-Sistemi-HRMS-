function updatePerson(){
    // Input elemanını seçme
    var nameInput = document.getElementById('yeni-ad');
    // Input'un değerini alma
    var nameValue = nameInput.value;
    console.log(nameValue);
    var idToUpdate=document.getElementById("update-person-info-id").textContent;
    console.log(idToUpdate);
    // PUT isteği gönderme
    fetch(`http://localhost:8080/api/v1/person/${idToUpdate}`, {
        method: 'PUT',
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
        alert(`${idToUpdate} kullanıcısı başarıyla güncellendi.`);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}