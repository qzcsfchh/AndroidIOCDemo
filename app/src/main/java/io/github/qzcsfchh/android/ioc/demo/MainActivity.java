package io.github.qzcsfchh.android.ioc.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import io.github.qzcsfchh.android.ioc.AbsService;
import io.github.qzcsfchh.android.ioc.IService;
import io.github.qzcsfchh.android.ioc.demo.databinding.ActivityMainBinding;
import me.hao.annotation.Router;


@Router("hahha")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvDex.setText(list2string(IOC.getServiceByDex(IService.class)));
        binding.tvDex.append("\n" + list2string(IOC.getServiceByDex(AbsService.class)));
        binding.tvSpi.setText(list2string(IOC.getServiceBySPI(IService.class)));
        binding.tvSpi.append("\n" + list2string(IOC.getServiceBySPI(AbsService.class)));
        binding.btnDex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvDex.setText(list2string(IOC.getServiceByDex(IService.class)));
            }
        });
        binding.btnSpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvSpi.setText(list2string(IOC.getServiceBySPI(IService.class)));
            }
        });
    }


    private String list2string(List<?> list) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb.append(o.getClass().getName()).append("\n");
        }
        return sb.toString();
    }
}