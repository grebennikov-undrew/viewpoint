import { httpRequest } from "./httpRequest";

export const deleteEntity = (address, id, showAlert) => {
    httpRequest.delete(`/${address}/${id}`)
    .then(response => {
        if (response.status === 400) {
            showAlert('Error: ' + response.data, "error");
            return;
        } 
        showAlert('Deleted successfully')
    })
    .catch(error => {
        console.log(error);
    });
}

export const getList = (address, showAlert, setData, onDenied = () => {}) => {
    httpRequest.get(`/${address}`)
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
        return;
    })
    .catch(error => {
        console.log(error);
    });
}
