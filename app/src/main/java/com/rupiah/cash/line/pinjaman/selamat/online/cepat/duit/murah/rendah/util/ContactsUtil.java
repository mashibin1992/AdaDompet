package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

public class ContactsUtil {

    public static JSONArray getContactsAll(Context context) throws JSONException {
        JSONArray contactsList = new JSONArray();
        String id;
        String mimetype;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts._ID}, null, null, null);
        if (null != cursor)//cursor不为空时执行下面所有代码
            while (cursor.moveToNext()) {
                org.json.JSONObject telphone = new org.json.JSONObject();
                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor contactInfoCursor = contentResolver.query(
                        ContactsContract.Data.CONTENT_URI, new String[]{}
                        , ContactsContract.Data.CONTACT_ID + "=" + id
                        , null
                        , null);
                telphone.put("contactId", id);
                if (null != contactInfoCursor) {
                    while (contactInfoCursor.moveToNext()) {
                        mimetype = contactInfoCursor.getString(
                                contactInfoCursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                        if (TextUtils.isEmpty(mimetype)) {
                            contactInfoCursor.close();
                            break;
                        }
                        if (mimetype.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))) {
                                int phoneType = contactInfoCursor.getInt(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))) {
                                    String phone = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    switch (phoneType) {
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                            telphone.put("mobile", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                            telphone.put("homeNum", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                            telphone.put("jobNum", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                                            telphone.put("workFax", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
                                            telphone.put("homeFax", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER:
                                            telphone.put("pager", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK:
                                            telphone.put("quickNum", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN:
                                            telphone.put("jobTel", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CAR:
                                            telphone.put("carNum", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN:
                                            telphone.put("isdn", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
                                            telphone.put("tel", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO:
                                            telphone.put("wirelessDev", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX:
                                            telphone.put("telegram", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD:
                                            telphone.put("tty_tdd", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
                                            telphone.put("jobMobile", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER:
                                            telphone.put("jobPager", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT:
                                            telphone.put("assistantNum", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MMS:
                                            telphone.put("mms", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                                            telphone.put("otherPhone", phone);
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX:
                                            telphone.put("otherFax", phone);
                                            break;
                                    }
                                }
                            }
                        } else if (mimetype.equals(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) {

                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME)))) {
                                String setDisplayName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
                                telphone.put("displayName", setDisplayName);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)))) {
                                String setLastName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                                telphone.put("lastName", setLastName);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.CONTACT_LAST_UPDATED_TIMESTAMP)))) {
                                String setLastUpdatedTimestamp = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.CONTACT_LAST_UPDATED_TIMESTAMP));
                                telphone.put("lastUpdatedTimestamp", setLastUpdatedTimestamp);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PREFIX)))) {
                                String setPrefix = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PREFIX));
                                telphone.put("prefix", setPrefix);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)))) {
                                String setFirstName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                                telphone.put("firstName", setFirstName);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME)))) {
                                String setMiddleName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME));
                                telphone.put("middleName", setMiddleName);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.SUFFIX)))) {
                                String setSuffix = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.SUFFIX));
                                telphone.put("suffix", setSuffix);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_FAMILY_NAME)))) {
                                String setPhoneticFirstName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_FAMILY_NAME));
                                telphone.put("phoneticFirstName", setPhoneticFirstName);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_MIDDLE_NAME)))) {
                                String setPhoneticMiddleName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_MIDDLE_NAME));
                                telphone.put("phoneticMiddleName", setPhoneticMiddleName);
                            }
                            if (!TextUtils.isEmpty(contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_GIVEN_NAME)))) {
                                String setPhoneticLastName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_GIVEN_NAME));
                                telphone.put("phoneticLastName", setPhoneticLastName);
                            }
                        } else if (mimetype.equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                            if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY))) {
                                String displayName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                                telphone.put("org", displayName);
                            } else if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE))) {
                                String displayName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                                telphone.put("title", displayName);
                            } else if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT))) {
                                String displayName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));
                                telphone.put("department", displayName);
                            }

                        } else if (mimetype.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))) {
                                int emailType = contactInfoCursor.getInt(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                                if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))) {
                                    String emailData = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                    switch (emailType) {
                                        case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                            telphone.put("workEmail", emailData);
                                            break;
                                        case ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM:
                                            telphone.put("customEmail", emailData);
                                            break;
                                        case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                                            telphone.put("mobileEmail", emailData);
                                            break;
                                        case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                            telphone.put("homeEmail", emailData);
                                            break;
                                        case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                                            telphone.put("otherEmail", emailData);
                                            break;
                                    }
                                }
                            }

                        } else if (mimetype.equals(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)) {
                            if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE))) {
                                String displayName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                                telphone.put("note", displayName);
                            }

                        } else if (ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE.equals(mimetype)) {
                            if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE))) {
                                int eventType = contactInfoCursor.getInt(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE));
                                if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE))) {
                                    String eventData = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                                    if (eventType == ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY) {
                                        telphone.put("birthday", eventData);
                                    } else if (eventType == ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY) {
                                        telphone.put("anniversary", eventData);
                                    } else if (eventType == ContactsContract.CommonDataKinds.Event.TYPE_OTHER) {
                                        telphone.put("other", eventData);
                                    }
                                }
                            }

                        } else if (ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE.equals(mimetype)) {
                            if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE))) {
                                int imType = contactInfoCursor.getInt(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                                if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA))) {
                                    String imData = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                                    if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_AIM) {
                                        telphone.put("aim", imData);
                                    } else if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_MSN) {
                                        telphone.put("msn", imData);
                                    } else if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_YAHOO) {
                                        telphone.put("yahoo", imData);
                                    } else if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_SKYPE) {
                                        telphone.put("skype", imData);
                                    } else if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ) {
                                        telphone.put("qq", imData);
                                    } else if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_GOOGLE_TALK) {
                                        telphone.put("googleTalk", imData);
                                    } else if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_ICQ) {
                                        telphone.put("icq", imData);
                                    } else if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_JABBER) {
                                        telphone.put("jabber", imData);
                                    } else if (imType == ContactsContract.CommonDataKinds.Im.PROTOCOL_NETMEETING) {
                                        telphone.put("netmeeting", imData);
                                    } else {
                                        telphone.put("otherIm", imData);
                                    }
                                }
                            }

                        } else if (ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE.equals(mimetype)) {
                            String displayName = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                            telphone.put("nickName", displayName);

                        } else if (ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE.equals(mimetype)) {
                            if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE))) {
                                int webType = contactInfoCursor.getInt(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE));
                                if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL))) {
                                    String webData = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                                    if (webType == ContactsContract.CommonDataKinds.Website.TYPE_CUSTOM) {
                                        telphone.put("customPage", webData);
                                    } else if (webType == ContactsContract.CommonDataKinds.Website.TYPE_HOME) {
                                        telphone.put("home", webData);
                                    } else if (webType == ContactsContract.CommonDataKinds.Website.TYPE_HOMEPAGE) {
                                        telphone.put("homePage", webData);
                                    } else if (webType == ContactsContract.CommonDataKinds.Website.TYPE_WORK) {
                                        telphone.put("workPage", webData);
                                    } else if (webType == ContactsContract.CommonDataKinds.Website.TYPE_FTP) {
                                        telphone.put("ftpPage", webData);
                                    } else if (webType == ContactsContract.CommonDataKinds.Website.TYPE_OTHER) {
                                        telphone.put("otherPage", webData);
                                    }
                                }
                            }

                        } else if (ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE.equals(mimetype)) {
                            if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE))) {
                                int structuredType = contactInfoCursor.getInt(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                                if (structuredType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK) {
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))) {
                                        telphone.put("workStreet", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))) {
                                        telphone.put("workCity", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX))) {
                                        telphone.put("workPobox", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD))) {
                                        telphone.put("workNeighborhood", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION))) {
                                        telphone.put("workRegion", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE))) {
                                        telphone.put("workStreet", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY))) {
                                        telphone.put("workCountry", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS))) {
                                        telphone.put("workFormattedAddress", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)));
                                    }
                                } else if (structuredType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME) {
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))) {
                                        telphone.put("homeStreet", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX))) {
                                        telphone.put("homePobox", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD))) {
                                        telphone.put("homeNeighborhood", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION))) {
                                        telphone.put("homeRegion", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE))) {
                                        telphone.put("homePostcode", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY))) {
                                        telphone.put("homeCountry", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS))) {
                                        telphone.put("homeFormattedAddress", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)));
                                    }
                                } else if (structuredType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER) {
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))) {
                                        telphone.put("otherStreet", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))) {
                                        telphone.put("otherCity", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX))) {
                                        telphone.put("otherPobox", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD))) {
                                        telphone.put("otherNeighborhood", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION))) {
                                        telphone.put("otherNeighborhood", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE))) {
                                        telphone.put("otherPostcode", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY))) {
                                        telphone.put("otherCountry", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)));
                                    }
                                    if (!contactInfoCursor.isNull(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS))) {
                                        telphone.put("otherFormattedAddress", contactInfoCursor.getString(contactInfoCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)));
                                    }
                                }
                            }
                        }
                    }
                    if (contactInfoCursor != null)
                        contactInfoCursor.close();
                }
                contactsList.put(telphone);
            }
        if (cursor != null)
            cursor.close();
        return contactsList;
    }
}
