import NumbersIcon from '@mui/icons-material/Numbers';
import AbcIcon from '@mui/icons-material/Abc';
import ScheduleIcon from '@mui/icons-material/Schedule';
import ToggleOnIcon from '@mui/icons-material/ToggleOn';
import DataObjectIcon from '@mui/icons-material/DataObject';

export const TypeIcon = (props) => {

    const {type} = props;

    if (type==="String") 
        return <AbcIcon {...props}/>
    if (type==="Double") 
        return <NumbersIcon {...props}/>
    if (type==="Timestamp") 
        return <ScheduleIcon {...props}/>
    if (type==="Boolean") 
        return <ToggleOnIcon {...props}/>

    return <DataObjectIcon {...props}/>
}

