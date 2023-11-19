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
