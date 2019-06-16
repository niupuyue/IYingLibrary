package com.niupuyue.mylibrary.widgets.chatkeyboard.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.renrui.job.R;
import com.renrui.job.util.UtilityException;
import com.renrui.libraries.util.UtilitySecurity;

import java.util.Arrays;
import java.util.List;

/**
 * 表情操作类
 */
public class UtilityExpression {

    private UtilityExpression() {
    }

    public static final int EMOJI_TYPE_CLASSICS = 0x0001;

    private static final int DEFAULT_EMOJI_SIZE = 62;

    public static final String EMOJI_TYPE_DELETE = "/:delete";

    public static List<String> emojiNameArray;

    // 表情名称
    public static String[] emojiNames = new String[]{"/:^_^", "/:^$^", "/:Q", "/:815", "/:809", "/:^O^", "/:081", "/:087", "/:086", "/:H", "/:012", "/:806", "/:b", "/:^x^", "/:814", "/:^W^", "/:080", "/:066", "/:807", "/:805", "/:071", "/:072", "/:065", "/:804", "/:813", "/:818", "/:015", "/:084", "/:801", "/:811", "/:?", "/:077", "/:083", "/:817", "/:!", "/:068", "/:079", "/:028", "/:026", "/:007", "/:816", "/:'\"\"", "/:802", "/:027", "/:(Zz...)", "/:*&*", "/:810", "/:>_<", "/:018", "/:>O<", "/:020", "/:044", "/:819", "/:085", "/:812", "/:\"", "/:>M<", "/:>@<", "/:076", "/:069", "/:O", "/:067", "/:043", "/:P", "/:808", "/:>W<", "/:073", "/:008", "/:803", "/:074", "/:O=O", "/:036", "/:039", "/:045", "/:046", "/:048", "/:047", "/:girl", "/:man", "/:052", "/:(OK)", "/:8*8", "/:)-(", "/:lip", "/:-F", "/:-W", "/:Y", "/:qp", "/:$", "/:%", "/:(&)", "/:@", "/:~B", "/:U*U", "/:clock", "/:R", "/:C", "/:plane", "/:075"};
    // 表情资源
    public static int[] emojiResId = new int[]{R.drawable.aliwx_s001, R.drawable.aliwx_s002, R.drawable.aliwx_s003, R.drawable.aliwx_s004, R.drawable.aliwx_s005, R.drawable.aliwx_s006, R.drawable.aliwx_s007, R.drawable.aliwx_s008, R.drawable.aliwx_s009, R.drawable.aliwx_s010, R.drawable.aliwx_s011, R.drawable.aliwx_s012, R.drawable.aliwx_s013, R.drawable.aliwx_s014, R.drawable.aliwx_s015, R.drawable.aliwx_s016, R.drawable.aliwx_s017, R.drawable.aliwx_s018, R.drawable.aliwx_s019, R.drawable.aliwx_s020, R.drawable.aliwx_s021, R.drawable.aliwx_s022, R.drawable.aliwx_s023, R.drawable.aliwx_s024, R.drawable.aliwx_s025, R.drawable.aliwx_s026, R.drawable.aliwx_s027, R.drawable.aliwx_s028, R.drawable.aliwx_s029, R.drawable.aliwx_s030, R.drawable.aliwx_s031, R.drawable.aliwx_s032, R.drawable.aliwx_s033, R.drawable.aliwx_s034, R.drawable.aliwx_s035, R.drawable.aliwx_s036, R.drawable.aliwx_s037, R.drawable.aliwx_s038, R.drawable.aliwx_s039, R.drawable.aliwx_s040, R.drawable.aliwx_s041, R.drawable.aliwx_s042, R.drawable.aliwx_s043, R.drawable.aliwx_s044, R.drawable.aliwx_s045, R.drawable.aliwx_s046, R.drawable.aliwx_s047, R.drawable.aliwx_s048, R.drawable.aliwx_s049, R.drawable.aliwx_s050, R.drawable.aliwx_s051, R.drawable.aliwx_s052, R.drawable.aliwx_s053, R.drawable.aliwx_s054, R.drawable.aliwx_s055, R.drawable.aliwx_s056, R.drawable.aliwx_s057, R.drawable.aliwx_s058, R.drawable.aliwx_s059, R.drawable.aliwx_s060, R.drawable.aliwx_s061, R.drawable.aliwx_s062, R.drawable.aliwx_s063, R.drawable.aliwx_s064, R.drawable.aliwx_s065, R.drawable.aliwx_s066, R.drawable.aliwx_s067, R.drawable.aliwx_s068, R.drawable.aliwx_s069, R.drawable.aliwx_s070, R.drawable.aliwx_s071, R.drawable.aliwx_s072, R.drawable.aliwx_s073, R.drawable.aliwx_s074, R.drawable.aliwx_s075, R.drawable.aliwx_s076, R.drawable.aliwx_s077, R.drawable.aliwx_s078, R.drawable.aliwx_s079, R.drawable.aliwx_s080, R.drawable.aliwx_s081, R.drawable.aliwx_s082, R.drawable.aliwx_s083, R.drawable.aliwx_s084, R.drawable.aliwx_s085, R.drawable.aliwx_s086, R.drawable.aliwx_s087, R.drawable.aliwx_s088, R.drawable.aliwx_s089, R.drawable.aliwx_s090, R.drawable.aliwx_s091, R.drawable.aliwx_s092, R.drawable.aliwx_s093, R.drawable.aliwx_s094, R.drawable.aliwx_s095, R.drawable.aliwx_s096, R.drawable.aliwx_s097, R.drawable.aliwx_s098, R.drawable.aliwx_s099};

    private static UtilityExpression instance;

    public static UtilityExpression getInstance() {
        if (instance == null) {
            instance = new UtilityExpression();
            // 初始化表情列表
            if (emojiNames != null && emojiNames.length > 0)
                emojiNameArray = Arrays.asList(emojiNames);
        }
        return instance;
    }

    // 根据表情名称获取表情的资源id
    public int getEmojiId(int emojiType, String emojiName) {
        Integer emojiId = null;
        switch (emojiType) {
            case EMOJI_TYPE_CLASSICS:
                try {
                    int index = -1;
                    if (!UtilitySecurity.isEmpty(emojiNameArray) && emojiNameArray.contains(emojiName)) {
                        index = emojiNameArray.indexOf(emojiName);
                    }
                    if (index >= 0) {
                        emojiId = emojiResId[index];
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            default:
                break;
        }
        return emojiId == null ? -1 : emojiId;
    }

    // 获取表情名称列表
    public List<String> getEmojiNameArray() {
        return emojiNameArray;
    }

    // 判断表情列表中是否存在某一表情名称
    public boolean isContainerEmojiName(String emojiName) {
        try {
            return getEmojiNameArray().contains(emojiName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 根据传递过来的字符串，修改成有表情的内容
     *
     * @param context     上下文对象
     * @param textView    需要执行显示的TextView/EditText
     * @param emotionType 传递的EmojiType
     * @param source      需要转换的字符
     * @return 转换之后的字符
     */
    public static SpannableString getEmojiContent(Context context, TextView textView, int emotionType, String source) {
        if (UtilitySecurity.isEmpty(source)) source = "";
        String emojiName;
        ImageSpan span;
        SpannableString spannableString = null;
        try {
            spannableString = new SpannableString(source);
            List<String> emojiNameArray = UtilityExpression.getInstance().getEmojiNameArray();
            if (UtilitySecurity.isEmpty(emojiNameArray)) {
                return spannableString;
            }
            int index = 0;// 查询需要执行的字符串中存在表情的起始位置
            for (int i = 0; i < emojiNameArray.size(); i++) {
                emojiName = emojiNameArray.get(i);// 获取list中的表情名称
                do {
                    index = source.indexOf(emojiName, index);
                    if (index >= 0) {
                        // 说明在字符串中存在这个表情，需要将字符串中的从index开始到(index+emojiName.length())之间的字符串转换成表情
                        span = scaleBitimap(context, textView, emotionType, emojiName);
                        if (span != null) {
                            spannableString.setSpan(span, index, (index + emojiName.length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            index = index + emojiName.length();// 继续查找
                        } else {
                            index++;
                        }
                    }
                } while (index >= 0 && index <= source.length() - emojiName.length());
                index = 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return spannableString;
    }

    /**
     * 根据传递的表情名称生成相应的ImageSpan对象
     *
     * @param context
     * @param textView
     * @param emotionType
     * @param emojiName
     * @return
     */
    public static ImageSpan scaleBitimap(Context context, TextView textView, int emotionType, String emojiName) {
        try {
            Resources res = context.getResources();
            Integer imageId = UtilityExpression.getInstance().getEmojiId(emotionType, emojiName);// 拿到当前表情的资源
            int size = DEFAULT_EMOJI_SIZE;
            if (textView != null) {
                size = (int) textView.getTextSize() * 13 / 10;// 设置压缩比例
            }
            Bitmap bitmap = BitmapFactory.decodeResource(res, imageId);// 根据资源生成bitmap对象
            Bitmap scaelBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);// 根据缩放比例获取缩放之后的图片
            ImageSpan span = new ImageSpan(context, scaelBitmap);// 将当前的表情生成为Span对象
            return span;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
