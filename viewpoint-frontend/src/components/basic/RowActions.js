import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

export const RowActions = ({onEdit, onDelete}) => {

    return(
        <div>
            <IconButton
                color="primary"
                aria-label="edit"
                onClick={onEdit}
            >
                <EditIcon />
            </IconButton>
            <IconButton
                color="error"
                aria-label="delete"
                onClick={onDelete}
            >
                <DeleteIcon />
            </IconButton>
            </div>
    )
}