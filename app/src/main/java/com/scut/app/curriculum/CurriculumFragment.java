package com.scut.app.curriculum;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.scut.app.curriculum.vm.CurriculumViewModel;
import com.scut.app.databinding.FragmentCurriculumBinding;
import com.scut.curriculum.ChangeActivity;
import com.scut.curriculum.CurriculumMainActivity;
import com.scut.curriculum.MyDatabaseHelper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 课表界面
 *
 * @author 徐鑫
 */
public class CurriculumFragment extends Fragment {
    private static final String TAG = "CurriculumFragment";

    private ImageButton findDevices;
    private MyDatabaseHelper dbHelper;
    public static List<Activity> activityList = new LinkedList();

    //内容数组
    String[] Array = {"第一周", "第二周", "第三周", "第四周", "第五周", "第六周", "第七周", "第八周", "第九周", "第十周", "第十一周",
            "第十二周", "第十三周", "第十四周", "第十五周", "第十六周", "第十七周", "第十八周", "第十九周", "第二十周"};


    private FragmentCurriculumBinding binding;
    private CurriculumViewModel mViewModel;

    public static CurriculumFragment newInstance() {
        return new CurriculumFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCurriculumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CurriculumViewModel.class);
        // 下面是数据库的操作，但是一旦建立好数据库之后就不用建立了，只需要插入数据就行
        dbHelper = new MyDatabaseHelper(requireContext(), "Schedule.db", null, 7);
        ImageButton createDatabase = binding.load;
        //通过点击事件得到一个类
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 必须执行操作之后才能被创建
                dbHelper.getWritableDatabase();
            }
        });

        //下面是插入数据的方式
        TextView addData = binding.insert;
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //从Excel中批量导入数据
                int num = 0;
                Workbook wb = null;
                Sheet sheet = null;
                Row row = null;
                String filePath = "/data/data/com.scut.app/information.xls";
                wb = readExcel(filePath);
                List<List<List<Object>>> list = new ArrayList<>();
                if (wb != null) {
                    try {
                        // 循环页签
                        for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
                            // 指定页签的值
                            sheet = wb.getSheetAt(sheetNum);
                            // 定义存放一个页签中所有数据的List
                            List<List<Object>> sheetList = new ArrayList<>();
                            num = sheet.getLastRowNum();
                            // 循环行
                            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                                // 指定行的值
                                row = sheet.getRow(rowNum);
                                List<Object> rowList = new ArrayList<>();
                                // 循环列
                                for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                                    Cell cell = sheet.getRow(rowNum).getCell(cellNum);
                                    rowList.add(getStringCellValue(cell));
                                }
                                sheetList.add(rowList);
                            }
                            list.add(sheetList);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装数据，我做一个示范
                //num是课程信息总数（行数）
                for (int i = 0; i < num; i++) {
                    int startWeek = (int) Float.parseFloat(list.get(0).get(i).get(4).toString());
                    int endWeek = (int) Float.parseFloat(list.get(0).get(i).get(5).toString());
                    int startTime = (int) Float.parseFloat(list.get(0).get(i).get(7).toString());
                    int endTime = (int) Float.parseFloat(list.get(0).get(i).get(8).toString());
                    for (int temp1 = startWeek; temp1 <= endWeek; temp1++) {
                        for (int temp2 = startTime; temp2 <= endTime; temp2++) {
                            values.put("class_name", list.get(0).get(i).get(0).toString());
                            values.put("credit", (int) Float.parseFloat(list.get(0).get(i).get(1).toString()));
                            values.put("classroom", list.get(0).get(i).get(2).toString());
                            values.put("teacher", list.get(0).get(i).get(3).toString());
                            values.put("lecture_time", temp2);
                            values.put("Week_of_class_time", temp1);
                            values.put("class_day", (int) Float.parseFloat(list.get(0).get(i).get(6).toString()));
                            db.insert("Schedule", null, values);
                            values.clear();
                        }
                    }
                }
            }

            //判断文件格式
            private Workbook readExcel(String filePath) {
                if (filePath == null) {
                    return null;
                }
                String extString = filePath.substring(filePath.lastIndexOf("."));

                try {
                    @SuppressWarnings("resource")
                    InputStream is = new FileInputStream(filePath);
                    if (".xls".equals(extString)) {
                        return new HSSFWorkbook(is);
                    } else if (".xlsx".equals(extString)) {
                        return new XSSFWorkbook(is);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @SuppressWarnings("deprecation")
            public String getStringCellValue(Cell cell) {
                String cellvalue = "";
                if (cell == null) {
                    return "";
                }
                switch (cell.getCellType()) {
                    case STRING:
                        cellvalue = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = cell.getDateCellValue();
                            cellvalue = sdf.format(date);
                        } else {
                            cellvalue = String.valueOf(cell.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        cellvalue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case BLANK:
                        cellvalue = "";
                        break;
                    default:
                        cellvalue = "";
                        break;
                }
                if (cellvalue == "") {
                    return "";
                }
                return cellvalue;
            }


        });

        //下面写增删改页面的转移按钮
        ImageButton imageButton = binding.setting;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ChangeActivity.class);
                requireActivity().startActivity(intent);
            }
        });

        // 删除所有数据的写法
        TextView deleteData = binding.delete;
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Schedule", "class_day = ?", new String[]{"1"});
                db.delete("Schedule", "class_day = ?", new String[]{"2"});
                db.delete("Schedule", "class_day = ?", new String[]{"3"});
                db.delete("Schedule", "class_day = ?", new String[]{"4"});
                db.delete("Schedule", "class_day = ?", new String[]{"5"});
                db.delete("Schedule", "class_day = ?", new String[]{"6"});
                db.delete("Schedule", "class_day = ?", new String[]{"7"});
            }
        });

        Spinner spinner = binding.spinner;

        //数组适配器
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Array);
        spinner.setAdapter(arrayAdapter);
        //设置默认选中项
        spinner.setSelection(0);
        Button buttonok = binding.accept;
        //设置按钮监听器
        buttonok.setOnClickListener(new MyonClickListener());
        // 定义按钮点击监听器
        CurriculumMainActivity.activityList.add(requireActivity());
    }

    private class MyonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == com.scut.curriculum.R.id.accept) {
                //获取选中项
                Spinner spinner = binding.spinner;
                //这里要留着修改课表的项目
                Toast.makeText(requireContext(), Array[spinner.getSelectedItemPosition()], Toast.LENGTH_SHORT).show();
                //先刷新，使得课程表上显示为空
//                view.findViewById(R.id.class_1_1);
                TextView view11 = binding.class11;
                view11.setText(" ");
                TextView view12 = binding.class12;
                view12.setText(" ");
                TextView view13 = binding.class13;
                view13.setText(" ");
                TextView view14 = binding.class14;
                view14.setText(" ");
                TextView view15 = binding.class15;
                view15.setText(" ");
                TextView view16 = binding.class16;
                view16.setText(" ");
                TextView view17 = binding.class17;
                view17.setText(" ");
                TextView view18 = binding.class18;
                view18.setText(" ");
                TextView view19 = binding.class19;
                view19.setText(" ");
                TextView view110 = binding.class110;
                view110.setText(" ");
                TextView view111 = binding.class111;
                view111.setText(" ");
                TextView view112 = binding.class112;
                view112.setText(" ");
                TextView view21 = binding.class21;
                view21.setText(" ");
                TextView view22 = binding.class22;
                view22.setText(" ");
                TextView view23 = binding.class23;
                view23.setText(" ");
                TextView view24 = binding.class24;
                view24.setText(" ");
                TextView view25 = binding.class25;
                view25.setText(" ");
                TextView view26 = binding.class26;
                view26.setText(" ");
                TextView view27 = binding.class27;
                view27.setText(" ");
                TextView view28 = binding.class28;
                view28.setText(" ");
                TextView view29 = binding.class29;
                view29.setText(" ");
                TextView view210 = binding.class210;
                view210.setText(" ");
                TextView view211 = binding.class211;
                view211.setText(" ");
                TextView view212 = binding.class212;
                view212.setText(" ");
                TextView view31 = binding.class31;
                view31.setText(" ");
                TextView view32 = binding.class32;
                view32.setText(" ");
                TextView view33 = binding.class33;
                view33.setText(" ");
                TextView view34 = binding.class34;
                view34.setText(" ");
                TextView view35 = binding.class35;
                view35.setText(" ");
                TextView view36 = binding.class36;
                view36.setText(" ");
                TextView view37 = binding.class37;
                view37.setText(" ");
                TextView view38 = binding.class38;
                view38.setText(" ");
                TextView view39 = binding.class39;
                view39.setText(" ");
                TextView view310 = binding.class310;
                view310.setText(" ");
                TextView view311 = binding.class311;
                view311.setText(" ");
                TextView view312 = binding.class312;
                view312.setText(" ");
                TextView view41 = binding.class41;
                view41.setText(" ");
                TextView view42 = binding.class42;
                view42.setText(" ");
                TextView view43 = binding.class43;
                view43.setText(" ");
                TextView view44 = binding.class44;
                view44.setText(" ");
                TextView view45 = binding.class45;
                view45.setText(" ");
                TextView view46 = binding.class46;
                view46.setText(" ");
                TextView view47 = binding.class47;
                view47.setText(" ");
                TextView view48 = binding.class48;
                view48.setText(" ");
                TextView view49 = binding.class49;
                view49.setText(" ");
                TextView view410 = binding.class410;
                view410.setText(" ");
                TextView view411 = binding.class411;
                view411.setText(" ");
                TextView view412 = binding.class412;
                view412.setText(" ");
                TextView view51 = binding.class51;
                view51.setText(" ");
                TextView view52 = binding.class52;
                view52.setText(" ");
                TextView view53 = binding.class53;
                view53.setText(" ");
                TextView view54 = binding.class54;
                view54.setText(" ");
                TextView view55 = binding.class55;
                view55.setText(" ");
                TextView view56 = binding.class56;
                view56.setText(" ");
                TextView view57 = binding.class57;
                view57.setText(" ");
                TextView view58 = binding.class58;
                view58.setText(" ");
                TextView view59 = binding.class59;
                view59.setText(" ");
                TextView view510 = binding.class510;
                view510.setText(" ");
                TextView view511 = binding.class511;
                view511.setText(" ");
                TextView view512 = binding.class512;
                view512.setText(" ");
                TextView view61 = binding.class61;
                view61.setText(" ");
                TextView view62 = binding.class62;
                view62.setText(" ");
                TextView view63 = binding.class63;
                view63.setText(" ");
                TextView view64 = binding.class64;
                view64.setText(" ");
                TextView view65 = binding.class65;
                view65.setText(" ");
                TextView view66 = binding.class66;
                view66.setText(" ");
                TextView view67 = binding.class67;
                view67.setText(" ");
                TextView view68 = binding.class68;
                view68.setText(" ");
                TextView view69 = binding.class69;
                view69.setText(" ");
                TextView view610 = binding.class610;
                view610.setText(" ");
                TextView view611 = binding.class611;
                view611.setText(" ");
                TextView view612 = binding.class612;
                view612.setText(" ");
                TextView view71 = binding.class71;
                view71.setText(" ");
                TextView view72 = binding.class72;
                view72.setText(" ");
                TextView view73 = binding.class73;
                view73.setText(" ");
                TextView view74 = binding.class74;
                view74.setText(" ");
                TextView view75 = binding.class75;
                view75.setText(" ");
                TextView view76 = binding.class76;
                view76.setText(" ");
                TextView view77 = binding.class77;
                view77.setText(" ");
                TextView view78 = binding.class78;
                view78.setText(" ");
                TextView view79 = binding.class79;
                view79.setText(" ");
                TextView view710 = binding.class710;
                view710.setText(" ");
                TextView view711 = binding.class711;
                view711.setText(" ");
                TextView view712 = binding.class712;
                view712.setText(" ");
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Schedule", null, null, null, null, null, null);


                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String class_name = cursor.getString(cursor.getColumnIndex("class_name"));
                        @SuppressLint("Range") String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
                        @SuppressLint("Range") String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                        @SuppressLint("Range") int Week_of_class_time = cursor.getInt(cursor.getColumnIndex("Week_of_class_time"));
                        @SuppressLint("Range") int class_day = cursor.getInt(cursor.getColumnIndex("class_day"));
                        @SuppressLint("Range") int lecture_time = cursor.getInt(cursor.getColumnIndex("lecture_time"));

                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 1) {
                            view11.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 2) {
                            view12.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 3) {
                            view13.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 4) {
                            view14.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 5) {
                            view15.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 6) {
                            view16.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 7) {
                            view17.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 8) {
                            view18.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 9) {
                            view19.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 10) {
                            view110.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 11) {
                            view111.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 12) {
                            view112.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 1) {
                            view21.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 2) {
                            view22.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 3) {
                            view23.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 4) {
                            view24.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 5) {
                            view25.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 6) {
                            view26.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 7) {
                            view27.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 8) {
                            view28.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 9) {
                            view29.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 10) {
                            view210.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 11) {
                            view211.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 12) {
                            view212.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 1) {
                            view31.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 2) {
                            view32.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 3) {
                            view33.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 4) {
                            view34.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 5) {
                            view35.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 6) {
                            view36.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 7) {
                            view37.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 8) {
                            view38.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 9) {
                            view39.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 10) {
                            view310.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 11) {
                            view311.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 12) {
                            view312.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 1) {
                            view41.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 2) {
                            view42.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 3) {
                            view43.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 4) {
                            view44.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 5) {
                            view45.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 6) {
                            view46.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 7) {
                            view47.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 8) {
                            view48.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 9) {
                            view49.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 10) {
                            view410.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 11) {
                            view411.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 12) {
                            view412.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 1) {
                            view51.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 2) {
                            view52.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 3) {
                            view53.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 4) {
                            view54.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 5) {
                            view55.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 6) {
                            view56.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 7) {
                            view57.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 8) {
                            view58.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 9) {
                            view59.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 10) {
                            view510.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 11) {
                            view511.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 12) {
                            view512.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 1) {
                            view61.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 2) {
                            view62.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 3) {
                            view63.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 4) {
                            view64.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 5) {
                            view65.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 6) {
                            view66.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 7) {
                            view67.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 8) {
                            view68.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 9) {
                            view69.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 10) {
                            view610.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 11) {
                            view611.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 12) {
                            view612.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 1) {
                            view71.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 2) {
                            view72.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 3) {
                            view73.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 4) {
                            view74.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 5) {
                            view75.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 6) {
                            view76.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 7) {
                            view77.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 8) {
                            view78.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 9) {
                            view79.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 10) {
                            view710.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 11) {
                            view711.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 12) {
                            view712.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }

                    } while (cursor.moveToNext());

                }
            }
        }
    }

    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}