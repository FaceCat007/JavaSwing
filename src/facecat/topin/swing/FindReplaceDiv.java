/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facecat.topin.swing;
import facecat.topin.core.*;
import facecat.topin.div.FCMenuItem;
import facecat.topin.input.*;
import facecat.topin.btn.*;
import facecat.topin.label.*;
import java.util.*;

public class FindReplaceDiv extends FCView implements FCEventCallBack, FCTouchEventCallBack {
    /// <summary>
    /// 构造函数
    /// </summary>
    public FindReplaceDiv()
    {
        setBorderColor(FCColor.None);
        setBackColor(FCColor.None);
        setHeight(30);
        setVisible(false);
        setAllowPreviewsEvent(true);
        setIsWindow(true);
    }

    public iTextBox m_iTextBox;

    /// <summary>
    /// 是否显示偏移
    /// </summary>
    /// <returns></returns>
    public boolean displayOffset()
    {
        return false;
    }

    public void reset()
    {
        m_input.setText("");
        m_input2.setText("");
    }

    private FCView m_topDiv;

    private FCView m_bottomDiv;

    public FCComboBox m_choose;

    public FCTextBox m_input;

    public FCTextBox m_input2;

    private FCLabel m_rpLabel;

    private FCButton m_caseButton;

    private FCComboBox m_caseSelect;

    private FCButton m_repButton;

    private FCButton m_repAllButton;

    private FCButton m_leftButton;

    private FCButton m_rightButton;

    private FCButton m_completeButton;

    /// <summary>
    /// 高亮信息
    /// </summary>
    public ArrayList<HighLightInfo> m_highLightInfos = new ArrayList<HighLightInfo>();

    public int m_selectedIndex = -1;

    public void onAdd()
    {
        super.onAdd();
        if (m_topDiv == null)
        {
            m_topDiv = new FCView();
            addView(m_topDiv);
            if (m_choose == null)
            {
                m_choose = new FCComboBox();
                m_topDiv.addView(m_choose);
                m_choose.addItem(new FCMenuItem("查找"));
                m_choose.addItem(new FCMenuItem("替换"));
                m_choose.setSelectedIndex(0);
                m_choose.addEvent(this, FCEventID.SelectedIndexChanged, this);
            }
            if (m_input == null)
            {
                m_input = new FCTextBox();
                m_topDiv.addView(m_input);
                m_input.setTempText("输入文字");
                m_input.addEvent(this, FCEventID.KeyDown, this);
            }
            if (m_caseButton == null)
            {
                m_caseButton = new FCButton();
                m_topDiv.addView(m_caseButton);
                m_caseButton.setText("Aa");
                m_caseButton.addEvent(this, FCEventID.Click, this);
            }
            if (m_caseSelect == null)
            {
                m_caseSelect = new FCComboBox();
                m_topDiv.addView(m_caseSelect);
                m_caseSelect.addItem(new FCMenuItem("包含字符"));
                m_caseSelect.addItem(new FCMenuItem("全字匹配"));
                m_caseSelect.setSelectedIndex(0);
            }
            if (m_leftButton == null)
            {
                m_leftButton = new FCButton();
                m_topDiv.addView(m_leftButton);
                m_leftButton.setText("<");
                m_leftButton.addEvent(this, FCEventID.Click, this);
            }
            if (m_rightButton == null)
            {
                m_rightButton = new FCButton();
                m_topDiv.addView(m_rightButton);
                m_rightButton.setText(">");
                m_rightButton.addEvent(this, FCEventID.Click, this);
            }
            if (m_completeButton == null)
            {
                m_completeButton = new FCButton();
                m_topDiv.addView(m_completeButton);
                m_completeButton.setText("完成");
                m_completeButton.addEvent(this, FCEventID.Click, this);
            }
        }
        if (m_bottomDiv == null)
        {
            m_bottomDiv = new FCView();
            addView(m_bottomDiv);
            if (m_rpLabel == null)
            {
                m_rpLabel = new FCLabel();
                m_rpLabel.setText("替换为...");
                m_rpLabel.setHeight(30);
                m_rpLabel.setAutoSize(false);
                m_rpLabel.setSize(new FCSize(80, 30));
                m_rpLabel.setBorderColor(FCColor.Border);
                m_rpLabel.setTextAlign(FCContentAlignment.MiddleLeft);
                m_bottomDiv.addView(m_rpLabel);
            }
            if (m_input2 == null)
            {
                m_input2 = new FCTextBox();
                m_bottomDiv.addView(m_input2);
                m_input2.setTempText("输入文字");
            }
            if (m_repButton == null)
            {
                m_repButton = new FCButton();
                m_bottomDiv.addView(m_repButton);
                m_repButton.setText("替换");
                m_repButton.addEvent(this, FCEventID.Click, this);
            }
            if (m_repAllButton == null)
            {
                m_repAllButton = new FCButton();
                m_bottomDiv.addView(m_repAllButton);
                m_repAllButton.setText("替换全部");
                m_repAllButton.addEvent(this, FCEventID.Click, this);
            }
        }
    }

    public void update()
    {
        super.update();
        int width = getWidth(), height = getHeight();
        if (m_iTextBox != null)
        {
            if (m_iTextBox.getVScrollBar() != null && m_iTextBox.getVScrollBar().isVisible())
            {
                width = width - m_iTextBox.getVScrollBar().getWidth();
            }
        }
        if (m_topDiv != null)
        {
            FCRect topBounds = new FCRect(0, 0, width, 30);
            m_topDiv.setBounds(topBounds);
            if (m_choose != null)
            {
                m_choose.setBounds(new FCRect(0, 0, 80, 30));
            }
            if (m_input != null)
            {
                m_input.setBounds(new FCRect(m_choose.getRight(), 0, width - 120, 30));
            }
            if (m_caseButton != null)
            {
                m_caseButton.setBounds(new FCRect(width - 120 - 120, 0, width - 90 - 120, 30));
            }
            if (m_caseSelect != null)
            {
                m_caseSelect.setBounds(new FCRect(width - 90 - 120, 0, width - 120, 30));
            }
            if (m_leftButton != null)
            {
                m_leftButton.setBounds(new FCRect(width - 120, 0, width - 90, 30));
            }
            if (m_rightButton != null)
            {
                m_rightButton.setBounds(new FCRect(width - 90, 0, width - 60, 30));
            }
            if (m_completeButton != null)
            {
                m_completeButton.setBounds(new FCRect(width - 60, 0, width, 30));
            }
        }
        if (m_bottomDiv != null)
        {
            FCRect bottomBounds = new FCRect(0, 30, width, 60);
            m_bottomDiv.setBounds(bottomBounds);
            if (m_rpLabel != null)
            {
                m_rpLabel.setBounds(new FCRect(0, 0, 80, 30));
            }
            if (m_input2 != null)
            {
                m_input2.setBounds(new FCRect(m_choose.getRight(), 0, width - 120, 30));
            }
            if (m_repButton != null)
            {
                m_repButton.setBounds(new FCRect(width - 120, 0, width - 60, 30));
            }
            if (m_repAllButton != null)
            {
                m_repAllButton.setBounds(new FCRect(width - 60, 0, width, 30));
            }
        }
    }

    public void showOrHideReplace(boolean showOrHide)
    {
        if (showOrHide)
        {
            setHeight(60);
        }
        else
        {
            setHeight(30);
        }
        FCPadding padding = m_iTextBox.getPadding();
        padding.top = getHeight();
        m_iTextBox.setPadding(padding);
        m_iTextBox.m_textChanged = true;
        m_iTextBox.update();
        m_iTextBox.invalidate();
    }

    public void callEvent(String eventName, Object sender, Object invoke)
    {
        if (sender == m_choose)
        {
            if (eventName.equals(FCEventID.SelectedIndexChanged))
            {
                int selectedIndex = m_choose.getSelectedIndex();
                if (selectedIndex == 0)
                {
                    showOrHideReplace(false);
                }
                else
                {
                    showOrHideReplace(true);
                }
                update();
                invalidate();
            }
        }
    }

    private void findAll(boolean autoSelect, boolean invalidate)
    {
        boolean ignoreCase = true;
        if (m_caseButton.getName().equals("1"))
        {
            ignoreCase = false;
        }
        m_highLightInfos.clear();
        String text = m_iTextBox.getText();
        String findText = m_input.getText();
        if (findText.length() > 0)
        {
            int selectedIndex = m_caseSelect.getSelectedIndex();
            if (selectedIndex == 0)
            {
                int pos = 0;
                while (true)
                {
                    int idx = -1;
                    if (ignoreCase)
                    {
                        idx = text.toLowerCase() .indexOf(findText.toLowerCase(), pos);
                    }
                    else
                    {
                        idx = text.indexOf(findText, pos);
                    }
                    if (idx != -1)
                    {
                        HighLightInfo highLightInfo = new HighLightInfo();
                        highLightInfo.m_start = idx;
                        highLightInfo.m_end = idx + findText.length() - 1;
                        m_highLightInfos.add(highLightInfo);
                        pos = idx + findText.length();
                    }
                    else
                    {
                        break;
                    }
                }
            }
            else
            {
                for (int i = 0; i < m_iTextBox.m_highLightInfos.size(); i++)
                {
                    String word = text.substring(m_iTextBox.m_highLightInfos.get(i).m_start, m_iTextBox.m_highLightInfos.get(i).m_end + 1);
                    if (ignoreCase)
                    {
                        if (word.toLowerCase().equals(findText.toLowerCase()))
                        {
                            m_highLightInfos.add(m_iTextBox.m_highLightInfos.get(i));
                        }
                    }
                    else
                    {
                        if (word.equals(findText))
                        {
                            m_highLightInfos.add(m_iTextBox.m_highLightInfos.get(i));
                        }
                    }
                }
            }
        }
        if (autoSelect)
        {
            if (m_highLightInfos.size() > 0)
            {
                m_selectedIndex = 0;
                autoJump();
            }
            else
            {
                m_selectedIndex = -1;
            }
        }
        if (invalidate)
        {
            m_iTextBox.update();
            m_iTextBox.invalidate();
        }
    }

    private void releaceAll()
    {
        int replaceCount = 0;
        while (true)
        {
            if (m_highLightInfos.size() > 0)
            {
                HighLightInfo highLightInfo = m_highLightInfos.get(m_selectedIndex);
                String findText = m_input.getText();
                String replaceText = m_input2.getText();
                String text = m_iTextBox.getText();
                String word = text.substring(highLightInfo.m_start, highLightInfo.m_end  + 1);
                boolean isEqual = word.equals(replaceText);
                if (!m_caseButton.getName().equals("1"))
                {
                    isEqual = word.toLowerCase().equals(replaceText.toLowerCase());
                }
                if (!isEqual)
                {
                    int subLen = findText.length() - replaceText.length();
                    if (replaceCount == 0)
                    {
                        iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(m_iTextBox.getText(), m_iTextBox.getSelectionStart(), m_iTextBox.getSelectionLength(), 0);
                        m_iTextBox.m_undoStack.push(newReoUndoInfo);
                    }
                    m_iTextBox.setSelectionStart(highLightInfo.m_start);
                    m_iTextBox.setSelectionLength(highLightInfo.m_end - highLightInfo.m_start + 1);
                    m_iTextBox.insertWord(replaceText);
                    for (int j = m_selectedIndex + 1; j < m_highLightInfos.size(); j++)
                    {
                        m_highLightInfos.get(j).m_start -= subLen;
                        m_highLightInfos.get(j).m_end -= subLen;
                    }
                    highLightInfo.m_end -= subLen;
                    findAll(false, false);
                    replaceCount++;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        if (replaceCount > 0)
        {
            m_iTextBox.m_textChanged = true;
            m_iTextBox.onTextChanged();
            findAll(false, true);
        }
    }

    private void replaceCurrent()
    {
        if (m_highLightInfos.size() > 0)
        {
            HighLightInfo highLightInfo = m_highLightInfos.get(m_selectedIndex);
            String findText = m_input.getText();
            String replaceText = m_input2.getText();
            String text = m_iTextBox.getText();
            String word = text.substring(highLightInfo.m_start, highLightInfo.m_end  + 1);
            boolean isEqual = word.equals(replaceText);
            if (!m_caseButton.getName().equals("1"))
            {
                isEqual = word.toLowerCase().equals(replaceText.toLowerCase());
            }
            if (!isEqual)
            {
                int subLen = findText.length() - replaceText.length();
                iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(m_iTextBox.getText(), m_iTextBox.getSelectionStart(), m_iTextBox.getSelectionLength(), 0);
                m_iTextBox.m_undoStack.push(newReoUndoInfo);
                m_iTextBox.setSelectionStart(highLightInfo.m_start);
                m_iTextBox.setSelectionLength(highLightInfo.m_end - highLightInfo.m_start + 1);
                m_iTextBox.insertWord(replaceText);
                for (int i = m_selectedIndex + 1; i < m_highLightInfos.size(); i++)
                {
                    m_highLightInfos.get(i).m_start -= subLen;
                    m_highLightInfos.get(i).m_end -= subLen;
                }
                highLightInfo.m_end -= subLen;
                m_iTextBox.m_textChanged = true;
                m_iTextBox.onTextChanged();
                findAll(false, true);
            }
        }
    }

    private void autoJump()
    {
        if (m_selectedIndex != -1 && m_highLightInfos.size() > 0)
        {
            HighLightInfo highLightInfo = m_highLightInfos.get(m_selectedIndex);
            boolean find = false;
            for (int i = 0; i < m_iTextBox.m_lines.size(); i++)
            {
                FCWordLine line = m_iTextBox.m_lines.get(i);
                for (int j = line.m_start; j <= line.m_end; j++)
                {
                    if (j >= highLightInfo.m_start && j <= highLightInfo.m_end)
                    {
                        if (!m_iTextBox.isLineVisible(j, 0.3f))
                        {
                            if (m_iTextBox.getVScrollBar() != null && m_iTextBox.getVScrollBar().isVisible())
                            {
                                int pos = (int)m_iTextBox.m_ranges.get(j).top - m_iTextBox.getVScrollBar().getPos();
                                if (pos < 40)
                                {
                                    m_iTextBox.getVScrollBar().setPos((int)m_iTextBox.m_ranges.get(j).top - 40);
                                }
                                else if (pos > m_iTextBox.getHeight() - 40)
                                {
                                    m_iTextBox.getVScrollBar().setPos((int)m_iTextBox.m_ranges.get(j).top + 40);
                                }
                            }
                        }
                        find = true;
                        break;
                    }
                }
                if (find)
                {
                    break;
                }
            }
        }
    }

    private void selectFront()
    {
        if (m_selectedIndex != -1 && m_highLightInfos.size() > 0)
        {
            m_selectedIndex--;
            if (m_selectedIndex < 0)
            {
                m_selectedIndex = m_highLightInfos.size() - 1;
            }
            autoJump();
            m_iTextBox.update();
            m_iTextBox.invalidate();
        }
    }

    private void selectNext()
    {
        if (m_selectedIndex != -1 && m_highLightInfos.size() > 0)
        {
            m_selectedIndex++;
            if (m_selectedIndex > m_highLightInfos.size() - 1)
            {
                m_selectedIndex = 0;
            }
            autoJump();
            m_iTextBox.update();
            m_iTextBox.invalidate();
        }
    }

    public void callTouchEvent(String eventName, Object sender, FCTouchInfo touchInfo, Object invoke)
    {
        if (sender == m_caseButton)
        {
            if (eventName.equals(FCEventID.Click))
            {
                if (!m_caseButton.getName().equals("1"))
                {
                    m_caseButton.setName("1");
                    m_caseButton.setTextColor(MyColor.USERCOLOR5);
                }
                else
                {
                    m_caseButton.setName("");
                    m_caseButton.setTextColor(FCColor.Text);
                }
                m_caseButton.invalidate();
            }
        }
        else if (sender == m_completeButton)
        {
            findAll(true, true);
        }
        else if (sender == m_repAllButton)
        {
            releaceAll();
        }
        else if (sender == m_repButton)
        {
            replaceCurrent();
        }
        else if (sender == m_leftButton)
        {
            selectFront();
        }
        else if (sender == m_rightButton)
        {
            selectNext();
        }
    }
}