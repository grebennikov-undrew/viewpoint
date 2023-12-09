import axios from 'axios'

// import { redirect, useNavigate } from "react-router-dom";
// const history= useNavigate();

const BASE_URL = '/api'

const instance = axios.create({
    withCredentials: true,
    baseURL: BASE_URL,
    validateStatus: function (status) {
        return status < 600;
    },
});

instance.interceptors.response.use(response => {
    if (response.status === 401) window.location.replace("/login");

    return response;
 }, error => {
    if (error.response.status === 401) window.location.replace("/login");
 });

export const httpRequest = instance;