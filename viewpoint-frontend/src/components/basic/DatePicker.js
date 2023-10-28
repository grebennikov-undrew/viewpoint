import { DatePicker as RawDataPicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import IconButton from '@mui/material/IconButton';
import ClearIcon from '@mui/icons-material/Clear';
import TextField from '@mui/material/TextField'
// import 'dayjs/locale/ru'
// import dayjs from 'dayjs';
// dayjs.locale('ru');



const DatePicker = (props) => {

    const { onClear } = props;
    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
        <RawDataPicker
            {...props}
            renderInput={(params) => (
            <div style={{ position: "relative", display: "inline-block" }} >
                <TextField {...params} />
                {props.value &&
                <IconButton style={{ position: "absolute", top: ".5rem", margin: "auto", right: "2rem" }} onClick={() => onClear()}>
                    <ClearIcon />
                </IconButton>
                }
            </div>
            )
            }
        /></LocalizationProvider >
    )
    
}


export default DatePicker;