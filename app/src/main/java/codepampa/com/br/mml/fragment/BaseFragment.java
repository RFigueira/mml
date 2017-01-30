package codepampa.com.br.mml.fragment;


import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import codepampa.com.br.mml.R;
import codepampa.com.br.mml.util.Util;

public abstract class BaseFragment extends Fragment {

    private AlertDialog dialog;


    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void alertOk(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title).setMessage(message);
        inflarAlert(builder);
    }

    protected void alertOk(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title).setMessage(message);
        inflarAlert(builder);
    }

    private void inflarAlert(AlertDialog.Builder builder) {
        builder.setPositiveButton(R.string.bottom_sheet_behavior, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void alertWait(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title).setMessage(message);
        dialog = builder.create();
        dialog.show();
    }

    protected void alertWaitDismiss() {
        if (!Util.isObjectsNull(dialog)) {
            dialog.dismiss();
        }
    }


}
