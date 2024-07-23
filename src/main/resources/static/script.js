var table;
var idToUpdate;
var iframeDocument;
document.addEventListener("DOMContentLoaded", function() {
    getPersons();
});
document.addEventListener('DOMContentLoaded', function() {
    var iframe = document.getElementById('update-iframe');
    iframe.onload = function() {
        console.log('iframe içeriği yüklendi.');
    };
});
function getPersons() {
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
            // `personTable`'ın `tbody` öğesini seçme
            const personDataList = document.querySelector("#personTable > tbody");

            // Tabloyu temizleme (isteğe bağlı)
            personDataList.innerHTML = '';

            // Verileri tabloya ekleme
            jsonArray.forEach(person => {
                // Yeni bir `tr` elementi oluşturma
                const tr = document.createElement('tr');

                // `name` ve `id` için `td` elementleri oluşturma
                const td1 = document.createElement('td');
                td1.textContent = person.name;
                tr.appendChild(td1);

                const td2 = document.createElement('td');
                td2.textContent = person.id;
                tr.appendChild(td2);

                // `tr` elementini `tbody`'ye ekleme
                personDataList.appendChild(tr);
            });

            // DataTables'ı başlatma
            table = $('#personTable').DataTable({
                select: {
                    style: 'single' // Çoklu seçim modunu etkinleştirir
                }
            })
        })
        .catch(error => {
            console.error('Error:', error); // Hata mesajını konsola yazdır
        });
}

function addPerson() {
    // Input elemanını seçme
    var nameInput = document.getElementById('textField-ekle');
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
        alert(`${nameValue} kullanıcısı başarıyla eklendi.`);
        // DataTables'ı başlatma
        document.getElementById('textField-ekle').value="";
        location.reload();
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}
function deletePerson(){
        // Seçili satırların sayısını al
        var selectedRowsCount = table.rows({ selected: true }).count();
        
        // Eğer seçili satır yoksa uyarı göster
        if (selectedRowsCount === 0) {
            alert("Öncelikle silinecek satırı seçiniz.");
            return; // İşlemi durdur
        }

        // Seçili satırın verilerini al
        var selectedRowData = table.row({ selected: true }).data();
        var id = selectedRowData[1]; // ID sütunundan alınır

        // DELETE isteği gönderme
        fetch(`http://localhost:8080/api/v1/person/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // Veriyi işleyin
            alert(`${id} kullanıcısı başarıyla silindi.`);
            // Satırı tablodan kaldır
            table.row({ selected: true }).remove().draw();
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function showOverlay() {
    // Seçili satırların sayısını al
    var selectedRowsCount = table.rows({ selected: true }).count();
    // Eğer seçili satır yoksa uyarı göster
    if (selectedRowsCount === 0) {
        alert("Öncelikle güncellenecek satırı seçiniz.");
        return; // İşlemi durdur
    }
    var selectedRowData = table.row({ selected: true }).data();
    var name = selectedRowData[0];// name 
    var id = selectedRowData[1]; // ID sütunundan alınır
    idToUpdate=id;
    var iframe = document.getElementById('update-iframe');
    iframeDocument = iframe.contentDocument;
    iframeDocument.getElementById("update-person-info-name").textContent = `${name}` ;
    iframeDocument.getElementById("update-person-info-id").textContent = `${id}` ;
    document.getElementById('overlay').style.display = 'flex';
    document.body.style.overflow = 'hidden';
    
}

function hideOverlay() {
    document.getElementById('overlay').style.display = 'none';
    document.body.style.overflow = '';
    location.reload();
}
