import { httpRequest } from "./httpRequest";

export const deleteEntity = (address, id, showAlert) => {
    httpRequest.delete(`/${address}/${id}`)
    .then(response => {
        if (response.status === 400) {
            showAlert('Error: ' + response.data, "error");
            return;
        } else if (response.status === 403) {
            showAlert('Permission denied');
            return;
        } 
        showAlert('Deleted successfully')
    })
    .catch(error => {
        console.log(error);
    });
}

export const getData = (address, showAlert, setData, onSuccess = () => {}, onDenied = () => {}, download=false) => {
    const responseType = download ? "blob" : "application/json"
    httpRequest.get(`/${address}`, {...responseType})
    .then(response => {
        if (response.status === 400) {
            showAlert('Error: ' + response.data, "error");
            return;
        } else if (response.status === 403) {
            showAlert("Permission denied");
            onDenied();
            return;
        }
        setData(response.data);
        onSuccess();
        return;
    })
    .catch(error => {
        console.log(error);
    });
}

export const postData = (address, data, showAlert, setData, onSuccess = () => {}, onDenied = () => {}) => {
    httpRequest.post(`/${address}`, data)
    .then(response => {
        if (response.status === 400) {
            showAlert('Error: ' + response.data, "error");
            return;
        } else if (response.status === 403) {
            showAlert("Permission denied");
            onDenied();
            return;
        }
        setData(response.data);
        onSuccess();
        return;
    })
    .catch(error => {
        console.log(error);
    });
}
