var table;
var idToUpdate;
var iframeDocument;

// iframe içeriği yüklendiğinde çalışacak fonksiyon
function onIframeLoad() {
    iframeDocument = document.getElementById('update-iframe').contentDocument;
    console.log('iframe içeriği yüklendi.');
}
document.addEventListener('DOMContentLoaded', function() {
    // Sayfanın URL'sini kontrol et
    if (window.location.pathname === '/dashboard') {
        getPersons();
    }else if (window.location.pathname === '/') {
    if(localStorage.getItem("token")!==null){
        window.location.href="/dashboard";
        }
    }
});

// DOMContentLoaded olayında iframe yükleme olayını bağlayın
document.addEventListener('DOMContentLoaded', function() {
    var iframe = document.getElementById('update-iframe');
    iframe.addEventListener('load', onIframeLoad);
});


function addPerson() {
    const token = localStorage.getItem('token');
    var nameInput = document.getElementById('textField-ekle');
    var nameValue = nameInput.value;

    if (nameValue.trim() === "") {
        alert('Name cannot be blank');
        return;
    }

    fetch('http://localhost:8080/addPerson', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ name: nameValue })
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 403) {
                    alert('Unauthorized access. Redirecting to the home page in 3s.');
                    setTimeout(3000);
                    window.location.href="/";
                }
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            alert(`${nameValue} kullanıcısı başarıyla eklendi.`);
            document.getElementById('textField-ekle').value = "";
            location.reload();
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function deletePerson() {
    const token = localStorage.getItem('token');
    var selectedRowsCount = table.rows({ selected: true }).count();

    if (selectedRowsCount === 0) {
        alert("Öncelikle silinecek satırı seçiniz.");
        return;
    }

    var selectedRowData = table.row({ selected: true }).data();
    var id = selectedRowData[1];

    fetch(`http://localhost:8080/delete/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 403) {
                    alert('Unauthorized access. Redirecting to the home page in 3s.');
                    setTimeout(3000);
                    window.location.href="/";
                }
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            alert(`${id} kullanıcısı başarıyla silindi.`);
            table.row({ selected: true }).remove().draw();
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function showOverlay() {
    var selectedRowsCount = table.rows({ selected: true }).count();
    if (selectedRowsCount === 0) {
        alert("Öncelikle güncellenecek satırı seçiniz.");
        return;
    }
    var selectedRowData = table.row({ selected: true }).data();
    var name = selectedRowData[0];
    var id = selectedRowData[1];
    idToUpdate = id;
    // iframe içeriğinin yüklendiğini kontrol edin
    var iframe = document.getElementById('update-iframe');
    if (iframeDocument) {
        iframeDocument.getElementById("update-person-info-name").textContent = `${name}`;
        iframeDocument.getElementById("update-person-info-id").textContent = `${id}`;
        document.getElementById('overlay').style.display = 'flex';
        document.body.style.overflow = 'hidden';
    } else {
        // iframe içeriği henüz yüklenmemişse hata mesajı veya yüklenene kadar bekleme
        alert('Iframe içeriği henüz yüklenmedi.');
    }
}

function hideOverlay() {
    document.getElementById('overlay').style.display = 'none';
    document.body.style.overflow = '';
    location.reload();
}

function login() {
    if(localStorage.getItem('token') !== null) {

    }
    var usernameValue = document.getElementById('username').value;
    var passwordValue = document.getElementById('password').value;

    fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: usernameValue, password: passwordValue })
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.message || 'Network response was not ok');
                });
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem('token', data.token);
            console.log(data);
            window.location.href = '/dashboard';
        })
        .catch(error => {
            alert(error.message);
        });
}
function getPersons() {
    const token = localStorage.getItem('token');
    fetch('http://localhost:8080/dashboard/getAllPerson', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 403) {
                    alert('Unauthorized access. Redirecting to the home page in 3s.');
                    setTimeout(3000);
                    window.location.href="/";
                }
            }
            return response.json();
        })
        .then(data => {
            const jsonArray = data;
            const personDataList = document.querySelector("#personTable > tbody");

            personDataList.innerHTML = '';

            jsonArray.forEach(person => {
                const tr = document.createElement('tr');
                const td1 = document.createElement('td');
                td1.textContent = person.name;
                tr.appendChild(td1);

                const td2 = document.createElement('td');
                td2.textContent = person.id;
                tr.appendChild(td2);

                personDataList.appendChild(tr);
            });

            table = $('#personTable').DataTable({
                select: {
                    style: 'single'
                }
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
